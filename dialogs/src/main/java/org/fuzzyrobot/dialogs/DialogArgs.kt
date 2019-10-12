package org.fuzzyrobot.dialogs

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DialogArgs(
    val title: StringValue? = null,
    val message: StringValue? = null,
    @StringRes val positive: Int? = android.R.string.ok,
    @StringRes val negative: Int? = null,
    val cancelable: Boolean = true,
    @StyleRes val style: Int = 0
) : Parcelable {

    constructor(
        @StringRes title: Int,
        @StringRes message: Int? = null,
        @StringRes positive: Int = android.R.string.ok,
        @StringRes negative: Int? = null,
        cancelable: Boolean = true,
        @StyleRes style: Int = 0
    ) : this(
        StringValue.of(title),
        StringValue.of(message), positive, negative, cancelable, style)


}