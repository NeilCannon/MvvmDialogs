package org.fuzzyrobot.dialogs

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import timber.log.Timber
import javax.inject.Singleton

@Module
interface MainModule {

    @Binds
    fun bindNotifier(toaster: Toaster): Notifier

}

@Singleton
@Component(modules = [MainModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: UserFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): ApplicationComponent
    }
}

val Activity.injector get() = (application as DaggerComponentProvider).component
val Fragment.injector get() = (activity?.application as DaggerComponentProvider).component

interface DaggerComponentProvider {
    val component: ApplicationComponent
}

open class App : Application(), DaggerComponentProvider {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTreeWIthMethod())
        }
    }

    override val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationContext(applicationContext)
            .build()
    }
}
