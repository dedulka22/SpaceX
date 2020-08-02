package com.teamtreehouse.spacex.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamtreehouse.spacex.data.api.ApiHelper
import com.teamtreehouse.spacex.data.repository.SpaceXRepository
import com.teamtreehouse.spacex.ui.missions.MissionsViewModel
import com.teamtreehouse.spacex.ui.rockets.RocketsViewModel
import com.teamtreehouse.spacex.ui.ships.ShipsViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MissionsViewModel::class.java)) {
            return MissionsViewModel(SpaceXRepository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(RocketsViewModel::class.java)) {
            return RocketsViewModel(SpaceXRepository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(ShipsViewModel::class.java)) {
            return ShipsViewModel(SpaceXRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}