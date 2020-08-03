package com.teamtreehouse.spacex.data.api

import com.teamtreehouse.spacex.data.model.Mission
import com.teamtreehouse.spacex.data.model.Rocket
import com.teamtreehouse.spacex.data.model.Ship
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceXApiService {
    @GET("/v3/missions")
    suspend fun getAllMissions(): List<Mission>

    @GET("/v3/missions/{mission_id}")
    suspend fun getDetailMission(@Path("mission_id") mission_id: String): Mission

    @GET("/v3/ships")
    suspend fun getAllShips(): List<Ship>

    @GET("/v3/rockets")
    suspend fun getAllRockets(): List<Rocket>
}