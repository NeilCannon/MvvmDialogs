package org.fuzzyrobot.dialogs

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

inline fun <reified F : Fragment> Fragment.showDialog(dialogArgs: DialogArgs) {
    val clazz = F::class.java
    val fragmentManager = parentFragmentManager
    showDialog(requireContext(), fragmentManager, clazz, dialogArgs)
}

inline fun <reified F : Fragment> FragmentActivity.showDialog(dialogArgs: DialogArgs) {
    val clazz = F::class.java
    val fragmentManager = supportFragmentManager
    showDialog(this, fragmentManager, clazz, dialogArgs)
}

inline fun <reified F : Fragment> showDialog(
    context: Context,
    fragmentManager: FragmentManager,
    clazz: Class<F>,
    dialogArgs: DialogArgs
) {
    val dialogFragment: DialogFragment =
        fragmentManager.fragmentFactory.instantiate(
            context.classLoader,
            clazz.canonicalName!!
        ) as DialogFragment
    dialogArgs?.let {
        dialogFragment.arguments = SimpleAlertDialogFragment.bundleOf(dialogArgs)
    }
    dialogFragment.show(fragmentManager, null)
}
