MvvmDialogs
===========

A little library to reduce the boilerplate involved with creating Android dialogs using MVVM Live Events. Handles creating the DialogFragment using AlertDialog and provides base MVVM Events.

Background
----------

When using the MVVM architecture, DialogFragments should communicate with their host Activity or Fragment using a shared ViewModel (in the Activity's scope). Dialog events (button clicks or user cancellation) need to be modelled as [Live Events](https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150), rather than as state that persists across configuration changes.



Example
-------

```kotlin
class SimplestDialogFragment : StandardAlertDialogFragment() {

    class SimplestViewModel : StandardDialogViewModel()

    override val viewModel: SimplestViewModel by activityViewModels()
}


class UserFragment : Fragment(R.layout.fragment_user) {

    private val simplestViewModel: SimplestDialogFragment.SimplestViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        simplestViewModel.userChoice.observeEvent(this) { result: Boolean ->
            // true if positive button was clicked
            Log.d(TAG, "$result")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // show dialog with minimal params (just a message)
        showDialog<SimplestDialogFragment>(DialogArgs(R.string.dialog_message))
    }
}

```

Test App
--------

The test app implements a yes/no dialog with a parameterized message to show how to pass more complex DialogArgs.

