package org.fuzzyrobot.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    // these ensure that LiveData can only be mutated from within a ViewModel

    protected fun <T> mutate(liveData: LiveData<T>, value: T) {
        (liveData as MutableLiveData<T>).postValue(value)
    }

    // convenience for LiveEvent<T>
    protected fun <T> fireEvent(liveEvent: LiveEvent<T>, value: T) {
        (liveEvent as MutableLiveEvent<T>).postValue(Event(value))
    }

    // convenience for LiveData<Unit>
    protected fun mutate(liveData: LiveData<Unit>) {
        mutate(liveData, Unit)
    }

    // convenience for LiveEvent<Unit>
    protected fun fireEvent(liveEvent: LiveEvent<Unit>) {
        fireEvent(liveEvent, Unit)
    }

}

