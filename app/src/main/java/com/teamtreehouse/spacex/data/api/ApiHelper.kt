package com.teamtreehouse.spacex.data.api

class ApiHelper(private val apiService: SpaceXApiService) {

    suspend fun getAllMissions() = apiService.getAllMissions()
    suspend fun getDetailMission(mission_id: String) = apiService.getDetailMission(mission_id)

    suspend fun getAllRockets() = apiService.getAllRockets()
    suspend fun getAllShips() = apiService.getAllShips()

}