package com.teamtreehouse.spacex.data.repository

import com.teamtreehouse.spacex.data.api.ApiHelper

class SpaceXRepository(private val apiHelper: ApiHelper) {
    suspend fun getAllMissions() = apiHelper.getAllMissions()
    suspend fun getDetailMission(mission_id: String) = apiHelper.getDetailMission(mission_id)

    suspend fun getAllRockets() = apiHelper.getAllRockets()
    suspend fun getAllShips() = apiHelper.getAllShips()
}