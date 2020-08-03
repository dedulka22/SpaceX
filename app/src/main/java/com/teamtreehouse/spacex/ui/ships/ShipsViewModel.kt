package com.teamtreehouse.spacex.ui.ships

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.teamtreehouse.spacex.data.repository.SpaceXRepository
import com.teamtreehouse.spacex.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ShipsViewModel(private val spaceXRepository: SpaceXRepository) : ViewModel() {

    fun fetchShips() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = spaceXRepository.getAllShips()))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
        }
    }
}