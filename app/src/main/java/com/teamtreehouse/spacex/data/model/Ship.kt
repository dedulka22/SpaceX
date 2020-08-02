package com.teamtreehouse.spacex.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ship(
    @SerializedName("ship_id") var id: String?,
    @SerializedName("ship_name") var name : String?,
    @SerializedName("roles") var role : List<String>?,
    @SerializedName("year_built") var year_built : Int?,
    @SerializedName("image") var image : String?,
    @SerializedName("ship_type") var ship_type : String?
) : Parcelable