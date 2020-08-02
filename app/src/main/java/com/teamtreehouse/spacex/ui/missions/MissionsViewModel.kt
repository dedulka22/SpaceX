package com.teamtreehouse.spacex.ui.missions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.teamtreehouse.spacex.data.model.Mission
import com.teamtreehouse.spacex.data.repository.SpaceXRepository
import com.teamtreehouse.spacex.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MissionsViewModel(private val spaceXRepository: SpaceXRepository) : ViewModel() {

    fun fetchMissions() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = spaceXRepository.getAllMissions()))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
        }
    }

    fun fetchDetailMission(mission_id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = spaceXRepository.getDetailMission(mission_id)))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
        }
    }

}