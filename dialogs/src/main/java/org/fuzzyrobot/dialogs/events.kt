package org.fuzzyrobot.dialogs

import androidx.lifecycle.*

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
data class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

}

typealias LiveEvent<T> = LiveData<Event<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner) {
        it.getContentIfNotHandled()?.let { newValue ->
            observer(newValue)
        }
    }
}

fun <T> LiveData<Event<T>>.observeEventForever(observer: (T) -> Unit) {
    observeForever {
        it?.getContentIfNotHandled()?.let { newValue ->
            observer(newValue)
        }
    }
}

fun LiveData<Event<Unit>>.observeEventForever(observer: () -> Unit) {
    observeForever {
        it?.getContentIfNotHandled()?.let {
            observer()
        }
    }
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer<T> {
        observer(it!!)
    })
}

fun <T> LiveData<T>.first(): LiveData<T> {
    var seen = false
    return Transformations.switchMap(this) { newValue ->
        if (seen) {
            null
        } else {
            seen = true
            MutableLiveData<T>().apply { value = newValue }
        }
    }
}

fun <T> LiveData<T>.filter(block: (T) -> Boolean): LiveData<T> {
    val filteredLiveData = MediatorLiveData<T>()
    filteredLiveData.addSource(this) {
        it?.let {
            if (block.invoke(it)) {
                filteredLiveData.value = it
            }
        }
    }

    return filteredLiveData
}

fun <T> LiveData<Event<T>>.filterEvent(block: (T) -> Boolean): LiveData<Event<T>> {
    val filteredLiveData = MediatorLiveData<Event<T>>()
    filteredLiveData.addSource(this) {
        it?.let {
            if (block.invoke(it.peekContent())) {
                filteredLiveData.value = it
            }
        }
    }

    return filteredLiveData
}

