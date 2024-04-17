package entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null
) : Parcelable {

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

//    override fun writeToParcel(p0: Parcel, p1: Int) {
//        TODO("Not yet implemented")
//    }
}
