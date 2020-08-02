package com.teamtreehouse.spacex.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mission(
    @SerializedName("mission_id") var id: String?,
    @SerializedName("mission_name") var name : String?,
    @SerializedName("manufacturers") var manufact: List<String>?,
    @SerializedName("payload_ids") var payload_ids: List<String>?,
    @SerializedName("wikipedia") var wiki: String?,
    @SerializedName("website") var web: String?,
    @SerializedName("twitter") var twitter: String?,
    @SerializedName("description") var desc: String?
) : Parcelable