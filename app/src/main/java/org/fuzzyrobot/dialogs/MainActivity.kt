package org.fuzzyrobot.dialogs

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commitNow
import javax.inject.Inject


interface Notifier {
    operator fun invoke(context: Context, text: CharSequence)
}

class Toaster @Inject constructor() : Notifier {
    override operator fun invoke(context: Context, text: CharSequence) =
        Toast.makeText(context, text, LENGTH_LONG).show()
}

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var appFragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        supportFragmentManager.fragmentFactory = appFragmentFactory

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                add<UserFragment>(R.id.frag_container)
            }
        }

    }

}



