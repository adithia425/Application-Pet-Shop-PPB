package Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CartItem(
    val productId: Int,
    val quantity: Int
) : Parcelable

