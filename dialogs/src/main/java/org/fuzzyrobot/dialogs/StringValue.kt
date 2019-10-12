package org.fuzzyrobot.dialogs

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

/**
 * a 'union' of:
 * - an Int (String resourceId)
 * - a CharSequence/String
 * Used to pass parcelable args to dialogs in a flexible way
 */
interface StringValue : Parcelable {

    companion object {
        fun of(@StringRes id: Int?): StringValue? = if (id == null) null else ResourceIdValue(id)
        fun of(@StringRes id: Int): StringValue = ResourceIdValue(id)
        fun of(value: CharSequence?): StringValue? = if (value == null) null else CharsValue(value.toString())

    }

    // use whichever type is actually available
    fun use(fInt: (Int) -> Unit, fChars: (CharSequence) -> Unit)
}

@Parcelize
private data class CharsValue(val str: String) : StringValue {

    override fun use(fInt: (Int) -> Unit, fChars: (CharSequence) -> Unit) {
        fChars(str)
    }
}

@Parcelize
private data class ResourceIdValue(val id: Int) : StringValue {

    override fun use(fInt: (Int) -> Unit, fChars: (CharSequence) -> Unit) {
        fInt(id)
    }
}