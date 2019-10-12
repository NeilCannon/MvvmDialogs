package org.fuzzyrobot.dialogs

import android.content.res.Resources
import androidx.fragment.app.activityViewModels
import javax.inject.Inject

class AreYouOkDialogFragment @Inject constructor() : StandardAlertDialogFragment() {

    companion object {
        fun args(resources: Resources, userName: String) = DialogArgs(
            title = StringValue.of(R.string.dialog_title),
            message = StringValue.of(resources.getString(R.string.dialog_message, userName)),
            positive = R.string.dialog_positive_button,
            negative = R.string.dialog_negative_button,
            cancelable = false
        )
    }

    class AreYouOkViewModel : StandardDialogViewModel()

    override val viewModel: AreYouOkViewModel by activityViewModels()

}