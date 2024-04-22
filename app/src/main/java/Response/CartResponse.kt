package Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartResponse(
    val cartId: Int,
    val totalItems: Int,
    val totalPrice: Double
) : Parcelable

