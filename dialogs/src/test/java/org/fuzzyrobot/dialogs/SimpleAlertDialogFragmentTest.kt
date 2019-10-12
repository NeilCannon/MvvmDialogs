package org.fuzzyrobot.dialogs

import android.app.AlertDialog
import android.os.Bundle
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

internal class SimpleAlertDialogFragmentTest {

    @Test
    fun `create`() {

        val dialogArgs = DialogArgs()
        val argsBundle = mock<Bundle> {
            on { getParcelable<DialogArgs>(SimpleAlertDialogFragment.FRAGMENT_ARGS_KEY) } doReturn dialogArgs
        }
        val standardDialogViewModel = mock<StandardDialogViewModel> {}
        val alertDialog = mock<AlertDialog> {}

        val dialogPopulator = mock<DialogPopulator> {
            on { populate(any(), any())} doAnswer {
                alertDialog
            }
        }

        val dialogFragment = object : SimpleAlertDialogFragment<StandardDialogViewModel>(dialogPopulator) {
            override val viewModel = standardDialogViewModel

            override fun onPositiveClicked() {}

        }.apply {
            arguments = argsBundle
        }

        assertEquals(alertDialog, dialogFragment.onCreateDialog(null))
    }
}