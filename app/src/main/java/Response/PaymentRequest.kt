package Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PaymentRequest(
    val amount: Double,
    val paymentMethod: String
) : Parcelable
