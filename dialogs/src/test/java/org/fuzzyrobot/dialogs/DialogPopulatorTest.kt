package org.fuzzyrobot.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test

internal class DialogPopulatorTest {

    private val context = mock<Context> { }

    val dialogFragment = mock<SimpleAlertDialogFragment<*>> {
        on { requireContext() } doReturn context
    }

    val alertDialog = mock<AlertDialog> {}

    val builder = mock<AlertDialog.Builder> {
        on { create() } doReturn alertDialog
    }

    val builderFactory = mock<AlertDialogBuilderFactory> {
        on { create(eq(context), any()) } doReturn builder
    }

    val populator = DialogPopulator(builderFactory)

    @Test
    fun `default, just positive button`() {
        val args = DialogArgs()

        populator.populate(dialogFragment, args)

        verify(builderFactory).create(context, args.style)
        verify(builder).setPositiveButton(eq(android.R.string.ok), any())
        verify(builder).create()
        verify(alertDialog).setCanceledOnTouchOutside(true)
        verify(dialogFragment).isCancelable = true

        verify(dialogFragment, atLeastOnce()).requireContext()
        verifyNoMoreInteractions(builderFactory, builder, alertDialog, dialogFragment)
    }

    @Test
    fun `all params`() {
        val args = DialogArgs(
            StringValue.of("title"),
            StringValue.of("message"), 1, 2, false
        )

        populator.populate(dialogFragment, args)

        verify(builderFactory).create(context, args.style)
        verify(builder).setTitle("title")
        verify(builder).setMessage("message")
        verify(builder).setPositiveButton(eq(1), any())
        verify(builder).setNegativeButton(eq(2), any())
        verify(builder).create()
        verify(alertDialog).setCanceledOnTouchOutside(false)
        verify(dialogFragment).isCancelable = false

        verify(dialogFragment, atLeastOnce()).requireContext()
        verifyNoMoreInteractions(builderFactory, builder, alertDialog, dialogFragment)
    }

    @Test
    fun `positive clicked`() {
        val args = DialogArgs()

        populator.populate(dialogFragment, args)

        reset(dialogFragment)
        argumentCaptor<DialogInterface.OnClickListener>().apply {

            verify(builder).setPositiveButton(eq(android.R.string.ok), capture())
            verify(dialogFragment, never()).onPositiveClicked()
            firstValue.onClick(alertDialog, 1)
            verify(dialogFragment).onPositiveClicked()
            verifyNoMoreInteractions(dialogFragment)
        }

    }

    @Test
    fun `negative clicked`() {
        val args = DialogArgs(negative = 1)

        populator.populate(dialogFragment, args)

        reset(dialogFragment)
        argumentCaptor<DialogInterface.OnClickListener>().apply {

            verify(builder).setNegativeButton(eq(1), capture())
            verify(dialogFragment, never()).onNegativeClicked()
            firstValue.onClick(alertDialog, 1)
            verify(dialogFragment).onNegativeClicked()
            verifyNoMoreInteractions(dialogFragment)
        }
    }


}