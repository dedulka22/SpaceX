package com.teamtreehouse.spacex.ui.missions.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.teamtreehouse.spacex.R.*
import com.teamtreehouse.spacex.data.model.Mission
import kotlinx.android.synthetic.main.item_mission.view.*

class MissionsAdapter(private var missions: ArrayList<Mission>
) : RecyclerView.Adapter<MissionsAdapter.MissionViewHolder>() {

    inner class MissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var EXTRA_MISSION = "mission_id"

        fun bind(item: Mission) {
            itemView.apply {
                txt_name.text = item.name
                txt_id.text = String.format(context.getString(string.mission_id), item.id)
                txt_manuf.text = String.format(context.getString(string.mission_manuf), fixedArrayToText(item.manufact.toString()))
                txt_payload.text = String.format(context.getString(string.mission_payload), fixedArrayToText(item.payload_ids.toString()))
            }

            itemView.setOnClickListener {
                val args = Bundle()
                args.putString(EXTRA_MISSION, item.id)
                it.findNavController().navigate(id.action_navigation_missions_to_missionDetailFragment, args)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder =
        MissionViewHolder(LayoutInflater.from(parent.context).inflate(layout.item_mission, parent, false))

    override fun getItemCount(): Int = missions.size

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        holder.bind(missions[position])
    }

    fun addDataM(missions: List<Mission>) {
        this.missions.apply {
            clear()
            addAll(missions)
        }
    }

    private fun fixedArrayToText(text: String): String {
        return text.replace("[", "").replace("]", "")
    }
}
