package org.fuzzyrobot.dialogs

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class StringValueTest {

    val fInt: (Int) -> Unit = mock {}
    val fChars: (CharSequence) -> Unit = mock {}

    @Test
    fun resourceIdValue() {
        val stringValue = StringValue.of(123)

        stringValue.use(fInt, fChars)

        verify(fInt)(123)
    }

    @Test
    fun charsValue() {
        val stringValue = StringValue.of("abc")

        stringValue!!.use(fInt, fChars)

        verify(fChars)("abc")
    }

}