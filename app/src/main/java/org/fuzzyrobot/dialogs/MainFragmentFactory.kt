package org.fuzzyrobot.dialogs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class MainFragmentFactory @Inject constructor(
    private val userFragmentProvider: Provider<UserFragment>,
    private val areYouOkDialogProvider: Provider<AreYouOkDialogFragment>,
    private val simplestDialogProvider: Provider<SimplestDialogFragment>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            UserFragment::class.java.canonicalName -> userFragmentProvider.get()
            AreYouOkDialogFragment::class.java.canonicalName -> areYouOkDialogProvider.get()
            SimplestDialogFragment::class.java.canonicalName -> simplestDialogProvider.get()

            else -> TODO("Missing fragment $className")
        }
    }
}