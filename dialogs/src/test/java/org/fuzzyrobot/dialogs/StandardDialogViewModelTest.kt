package org.fuzzyrobot.dialogs

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test

class StandardDialogViewModelTest {

    val vm = StandardDialogViewModel()
    val positiveObserver = mock<Observer<Event<Unit>>>()
    val negativeObserver = mock<Observer<Event<Unit>>>()
    val cancelledObserver = mock<Observer<Event<Unit>>>()
    val userChoiceObserver = mock<Observer<Event<Boolean>>>()

    @Before
    fun setUp() {
        ArchTaskExecutor.getInstance().setDelegate(FauxMainThreadExecutor)
        vm.positive.observeForever(positiveObserver)
        vm.negative.observeForever(negativeObserver)
        vm.cancelled.observeForever(cancelledObserver)
        vm.userChoice.observeForever(userChoiceObserver)
    }

    @Test
    fun positive() {

        vm.positive()

        FauxMainThreadExecutor.sync()

        verify(positiveObserver).onChanged(Event(Unit))
        verify(userChoiceObserver).onChanged(Event(true))
        verifyNoMoreInteractions(
            positiveObserver,
            negativeObserver,
            cancelledObserver,
            userChoiceObserver
        )
    }

    @Test
    fun negative() {

        vm.negative()

        FauxMainThreadExecutor.sync()

        verify(negativeObserver).onChanged(Event(Unit))
        verify(userChoiceObserver).onChanged(Event(false))
        verifyNoMoreInteractions(
            positiveObserver,
            negativeObserver,
            cancelledObserver,
            userChoiceObserver
        )
    }

    @Test
    fun cancelled() {

        vm.cancelled()
        FauxMainThreadExecutor.sync()

        verify(cancelledObserver).onChanged(Event(Unit))
        verify(userChoiceObserver).onChanged(Event(false))
        verifyNoMoreInteractions(
            positiveObserver,
            negativeObserver,
            cancelledObserver,
            userChoiceObserver
        )
    }


}