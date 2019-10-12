package org.fuzzyrobot.dialogs

import android.app.AlertDialog
import android.content.Context
import androidx.annotation.StyleRes

open class AlertDialogBuilderFactory {
    open fun create(context: Context, @StyleRes style: Int = 0): AlertDialog.Builder =
        AlertDialog.Builder(context, style)
}

open class DialogPopulator(private val builderFactory: AlertDialogBuilderFactory = AlertDialogBuilderFactory()) {

    fun populate(dialogFragment: SimpleAlertDialogFragment<*>, args: DialogArgs): AlertDialog? {
        val builder = builderFactory.create(dialogFragment.requireContext(), args.style)
        return builder
            .apply {
                args.title?.use({ setTitle(it) }, { setTitle(it) })
                args.message?.use({ setMessage(it) }, { setMessage(it) })
            }
            .apply {
                args.positive?.let {
                    setPositiveButton(it) { _, _ -> dialogFragment.onPositiveClicked() }
                }
                args.negative?.let {
                    setNegativeButton(it) { _, _ -> dialogFragment.onNegativeClicked() }
                }
            }
            .create().apply {
                setCanceledOnTouchOutside(args.cancelable)
                dialogFragment.isCancelable = args.cancelable
            }
    }

}