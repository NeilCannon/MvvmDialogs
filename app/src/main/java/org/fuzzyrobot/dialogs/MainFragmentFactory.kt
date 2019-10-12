package org.fuzzyrobot.dialogs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class MainFragmentFactory @Inject constructor(private val userFragmentProvider: Provider<UserFragment>,
                                              private val areYouOkDialogProvider: Provider<AreYouOkDialogFragment>) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        Timber.d("$className")
        return when (className) {
            UserFragment::class.java.canonicalName -> userFragmentProvider.get()
            AreYouOkDialogFragment::class.java.canonicalName -> areYouOkDialogProvider.get()

            else -> TODO("Missing fragment $className")
        }
    }
}