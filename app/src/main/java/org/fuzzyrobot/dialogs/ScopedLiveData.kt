package org.fuzzyrobot.dialogs

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData

interface ScopedLiveData<T> {
    fun observe(observer: (T) -> Unit)
}

fun <T> FragmentActivity.scoped(liveData: LiveData<T>): ScopedLiveData<T> = object :
    ScopedLiveData<T> {
    override fun observe(observer: (T) -> Unit) {
        liveData.observe(this@scoped, observer)
    }

}