package Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    val username: String,
    val email: String,
    val fullName: String
) : Parcelable