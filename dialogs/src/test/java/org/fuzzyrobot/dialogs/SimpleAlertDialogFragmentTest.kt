package org.fuzzyrobot.dialogs

import android.app.AlertDialog
import android.os.Bundle
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.Test

internal class SimpleAlertDialogFragmentTest {

    val dialogArgs = DialogArgs()
    val argsBundle = mock<Bundle> {
        on { getParcelable<DialogArgs>(SimpleAlertDialogFragment.FRAGMENT_ARGS_KEY) } doReturn dialogArgs
    }
    val standardDialogViewModel = mock<StandardDialogViewModel> {}
    val alertDialog = mock<AlertDialog> {}

    val dialogPopulator = mock<DialogPopulator> {
        on { populate(any(), any()) } doAnswer {
            alertDialog
        }
    }

    var cancelledCalled = false
    val dialogFragment =
        object : SimpleAlertDialogFragment<StandardDialogViewModel>(dialogPopulator) {
            override val viewModel = standardDialogViewModel

            override fun onPositiveClicked() {}
            override fun onCancelled() { cancelledCalled = true}

        }.apply {
            arguments = argsBundle
        }

    @Test
    fun `create`() {
        assertEquals(alertDialog, dialogFragment.onCreateDialog(null))
    }

    @Test
    fun cancelled() {
        dialogFragment.onCancel(alertDialog)
        assertTrue(cancelledCalled)

    }
}