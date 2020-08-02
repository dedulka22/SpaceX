package com.teamtreehouse.spacex.ui.missions.adapter

import com.teamtreehouse.spacex.data.model.Mission

interface OnMissionItemClicked {
    fun onMissionItemClicked(mission_id: String)
}