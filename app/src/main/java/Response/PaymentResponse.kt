package Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PaymentResponse(
    val transactionId: String,
    val status: String
) : Parcelable
