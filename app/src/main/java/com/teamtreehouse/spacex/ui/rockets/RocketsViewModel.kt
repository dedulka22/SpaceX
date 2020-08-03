package com.teamtreehouse.spacex.ui.rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.teamtreehouse.spacex.data.repository.SpaceXRepository
import com.teamtreehouse.spacex.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class RocketsViewModel(private val spaceXRepository: SpaceXRepository) : ViewModel() {

    fun fetchRockets() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = spaceXRepository.getAllRockets()))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
        }
    }
}