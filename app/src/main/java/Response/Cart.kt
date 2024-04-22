package Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Cart(
    val cartItems: List<CartItem>,
    val totalItems: Int,
    val totalPrice: Double
) : Parcelable
