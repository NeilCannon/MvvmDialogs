package org.fuzzyrobot.dialogs.app

import android.content.res.Resources
import androidx.fragment.app.activityViewModels
import org.fuzzyrobot.dialogs.StandardAlertDialogFragment
import org.fuzzyrobot.dialogs.StandardDialogViewModel
import javax.inject.Inject

class SimplestDialogFragment @Inject constructor() : StandardAlertDialogFragment() {

    class SimplestViewModel : StandardDialogViewModel()

    override val viewModel: SimplestViewModel by activityViewModels()

}