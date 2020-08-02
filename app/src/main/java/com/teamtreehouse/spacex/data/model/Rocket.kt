package com.teamtreehouse.spacex.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Rocket(
    @SerializedName("rocket_name") var name: String?,
    @SerializedName("id") var id: String?,
    @SerializedName("active") var active: Boolean?,
    @SerializedName("cost_per_launch") var cost: Int?,
    @SerializedName("first_flight") var flight: Date?,
    @SerializedName("country") var country: String?,
    @SerializedName("company") var company: String?,
    @SerializedName("height") var height: Height?,
    @SerializedName("diameter") var diameter: Diameter?,
    @SerializedName("mass") var mass: Mass?
) : Parcelable

@Parcelize
data class Height(
    @SerializedName("meters") var meters: Double?,
    @SerializedName("feet") var feet: Double?
) : Parcelable

@Parcelize
data class Diameter(
    @SerializedName("meters") var meters: Double?,
    @SerializedName("feet") var feet: Double?
) : Parcelable

@Parcelize
data class Mass(
    @SerializedName("kg") var kg: Int?
) : Parcelable