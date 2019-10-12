package org.fuzzyrobot.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get

inline fun <reified VM : ViewModel> FragmentActivity.getViewModel() = ViewModelProvider(this).get<VM>()

abstract class SimpleAlertDialogFragment<VM>(val dialogPopulator: DialogPopulator = DialogPopulator()) :
    DialogFragment() {

    companion object {
        const val FRAGMENT_ARGS_KEY = "ARGS"

        // all args passed via a single parcelable using FRAGMENT_ARGS_KEY
        fun bundleOf(args: DialogArgs) = bundleOf(FRAGMENT_ARGS_KEY to args)
    }

    abstract val viewModel: VM

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments?.getParcelable<DialogArgs>(FRAGMENT_ARGS_KEY)

        return args?.let {
            dialogPopulator.populate(this, args)
        } ?: throw IllegalArgumentException()
    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelled()
    }

    abstract fun onPositiveClicked()
    open fun onNegativeClicked() {}
    open fun onCancelled() {}

}