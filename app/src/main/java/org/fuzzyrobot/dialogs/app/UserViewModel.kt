package org.fuzzyrobot.dialogs.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.fuzzyrobot.dialogs.BaseViewModel

class UserViewModel : BaseViewModel() {

    val userIsOk: LiveData<Boolean> = MutableLiveData()

    fun userIsOk(ok: Boolean) {
        mutate(userIsOk, ok)
    }
}