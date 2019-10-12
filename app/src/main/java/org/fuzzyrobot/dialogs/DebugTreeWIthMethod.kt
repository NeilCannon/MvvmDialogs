package org.fuzzyrobot.dialogs

import android.os.Build
import timber.log.Timber
import java.util.regex.Pattern

/* a small mod to Timber.DebugTree that logs method name as well as class
 * so:
 * Timber.d("value is %d", value)
 * logs:
 * D/ClientCodeActivity.onCreate: value is 5
 * instead of:
 * D/ClientCodeActivity: value is 5
 *
 * log messages can't be empty, so to just log the fact that a method has been hit, do:
 * Timber.d("-")
 *
 * to set up:
 * Timber.plant(DebugTreeWIthMethod())
 */
open class DebugTreeWIthMethod : Timber.DebugTree() {
    companion object {
        private const val MAX_TAG_LENGTH = 23
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

        fun enableTestLogging() {

            Timber.uprootAll()

            // send Timber logging to stdout
            Timber.plant(object : DebugTreeWIthMethod() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    println("$tag $message")
                }
            })

        }
    }

    override fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1) + "." + element.methodName
        // Tag length limit was removed in API 24.
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tag
        } else tag.substring(0, MAX_TAG_LENGTH)
    }

}