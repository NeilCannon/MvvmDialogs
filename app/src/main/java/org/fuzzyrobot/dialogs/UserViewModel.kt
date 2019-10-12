package org.fuzzyrobot.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserViewModel : BaseViewModel() {

    val userIsOk: LiveData<Boolean> = MutableLiveData()

    fun userIsOk(ok: Boolean) {
        mutate(userIsOk, ok)
    }
}