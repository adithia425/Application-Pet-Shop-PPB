package data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item (
    var id: String,
    var name: String,
    var description: String,
    var price: Int,
    var photo: String
): Parcelable