package org.fuzzyrobot.dialogs


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_user.*
import timber.log.Timber
import javax.inject.Inject

class UserFragment @Inject constructor() : Fragment(R.layout.fragment_user) {

    private val areYouOkDialogViewModel: AreYouOkDialogFragment.AreYouOkViewModel by activityViewModels()
    private val okViewModel: UserViewModel by activityViewModels()

    @Inject
    lateinit var notifier: Notifier


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)

        areYouOkDialogViewModel.userChoice.observeEvent(this) {
            okViewModel.userIsOk(it)
        }

        okViewModel.userIsOk.observe(this) {
            val message =
                if (it) getString(R.string.user_ok_msg_good) else getString(R.string.user_ok_msg_bad)
            notifier(requireContext(), message)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        show_dialog_button.setOnClickListener {
            showDialog<AreYouOkDialogFragment>(AreYouOkDialogFragment.args(resources, "Neil"))
        }

    }

}


