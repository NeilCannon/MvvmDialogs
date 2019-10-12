package org.fuzzyrobot.dialogs

open class StandardDialogViewModel : BaseViewModel() {
    val positive: LiveEvent<Unit> = MutableLiveEvent()
    val negative: LiveEvent<Unit> = MutableLiveEvent()
    val cancelled: LiveEvent<Unit> = MutableLiveEvent()

    // alternative: emits true for positive, false for negative/cancelled
    val userChoice: LiveEvent<Boolean> = MutableLiveEvent()

    fun positive() {
        fireEvent(positive)
        fireEvent(userChoice, true)
    }

    fun negative() {
        fireEvent(negative)
        fireEvent(userChoice, false)
    }

    fun cancelled() {
        fireEvent(cancelled)
        fireEvent(userChoice, false)
    }

}