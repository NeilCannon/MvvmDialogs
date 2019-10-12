package org.fuzzyrobot.dialogs

abstract class StandardAlertDialogFragment() : SimpleAlertDialogFragment<StandardDialogViewModel>() {

    override fun onPositiveClicked() = viewModel.positive()

    override fun onNegativeClicked() = viewModel.negative()

    override fun onCancelled() = viewModel.cancelled()

}