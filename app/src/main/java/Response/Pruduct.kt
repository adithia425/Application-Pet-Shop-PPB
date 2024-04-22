package Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val price: Double
) : Parcelable

