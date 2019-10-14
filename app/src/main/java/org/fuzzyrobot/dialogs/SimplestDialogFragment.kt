package org.fuzzyrobot.dialogs

import android.content.res.Resources
import androidx.fragment.app.activityViewModels
import javax.inject.Inject

class SimplestDialogFragment @Inject constructor() : StandardAlertDialogFragment() {

    class SimplestViewModel : StandardDialogViewModel()

    override val viewModel: SimplestViewModel by activityViewModels()

}