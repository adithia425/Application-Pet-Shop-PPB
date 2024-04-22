package Response
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Tambahkan anotasi @Parcelize
@Parcelize
data class LoginResponse(
    val token: String,
    val userId: String
) : Parcelable