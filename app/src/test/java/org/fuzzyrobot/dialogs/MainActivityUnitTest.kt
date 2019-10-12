package org.fuzzyrobot.dialogs

import android.content.Context
import android.os.Bundle
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dagger.*
import kotlinx.android.synthetic.main.fragment_user.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import javax.inject.Singleton

@Module
object FakeMainModule {

    val myDialogFragment = mock<AreYouOkDialogFragment> {}
    val notifier = mock<Notifier> {}

    @Provides
    @JvmStatic
    @Reusable
    fun provideMyDialogFragment(): AreYouOkDialogFragment = myDialogFragment

    @Provides
    @JvmStatic
    @Reusable
    fun provideNotifier(): Notifier = notifier

}


@Singleton
@Component(modules = [FakeMainModule::class])
internal interface TestComponent : ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): ApplicationComponent
    }

}

class TestApp : App() {

    override val component: ApplicationComponent by lazy {
        DaggerTestComponent.builder()
            .applicationContext(applicationContext)
            .build()
    }

}

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, sdk = [28])
class MainActivityUnitTest {

    lateinit var activity: MainActivity
    lateinit var activityController: ActivityController<MainActivity>

    val myDialogViewModel: AreYouOkDialogFragment.AreYouOkViewModel
        get() = activity.getViewModel()

    val okViewModel: UserViewModel
        get() = activity.getViewModel()


    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(MainActivity::class.java)
        activity = activityController.get()
        activityController.create().start().resume().visible()
    }

    @Test
    fun `click shows dialog`() {
        activity.show_dialog_button.performClick()

        verify(FakeMainModule.myDialogFragment).show(activity.supportFragmentManager, null)
        argumentCaptor<Bundle>().apply {
            verify(FakeMainModule.myDialogFragment).arguments = capture()
            val bundle = firstValue
            assertEquals(
                AreYouOkDialogFragment.args(activity.resources, "Neil"),
                bundle[SimpleAlertDialogFragment.FRAGMENT_ARGS_KEY]
            )
        }
    }

    @Test
    fun `dialog ok changes view model`() {
        assertNull(okViewModel.userIsOk.value)

        myDialogViewModel.positive()

        assertTrue(okViewModel.userIsOk.value!!)
    }

    @Test
    fun `view model change notifies user, ok`() {

        okViewModel.userIsOk(true)

        verify(FakeMainModule.notifier)(activity, "Good to Know")
    }

    @Test
    fun `view model change notifies user, not ok`() {

        okViewModel.userIsOk(false)

        verify(FakeMainModule.notifier)(activity, "Oh Dear")
    }

}
