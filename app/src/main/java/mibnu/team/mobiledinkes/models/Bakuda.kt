package mibnu.team.mobiledinkes.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bakuda(
    @SerializedName("nama") var name : String? = null,
    @SerializedName("username") var username: String? = null
) : Parcelable