package data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item (
    var id: Int,
    var name: String,
    var description: String,
    var photo: String
): Parcelable