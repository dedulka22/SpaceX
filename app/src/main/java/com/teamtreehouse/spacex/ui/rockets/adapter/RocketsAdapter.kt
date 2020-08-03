package com.teamtreehouse.spacex.ui.rockets.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamtreehouse.spacex.data.model.Rocket
import com.teamtreehouse.spacex.R.*
import kotlinx.android.synthetic.main.item_rocket.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class RocketsAdapter(private var rockets: ArrayList<Rocket>
) : RecyclerView.Adapter<RocketsAdapter.RocketViewHolder>() {

    inner class RocketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Rocket) {
            itemView.apply {
                txt_name.text = item.name

                item.active?.let {
                    if (it) {
                        txt_status.text = String.format(
                            context.getString(string.rocket_status),
                            context.getString(string.status_active)
                        )
                        txt_status.setTextColor(Color.GREEN)
                    } else {
                        txt_status.text = String.format(
                            context.getString(string.rocket_status),
                            context.getString(string.status_inactive)
                        )
                        txt_status.setTextColor(Color.RED)
                    }
                }

                txt_costPerLaunch.text = String.format(
                    context.getString(string.rocket_cost_per_launch),
                    formatCost(item.cost ?: 0))
                txt_rocketHeight.text = String.format(
                    context.getString(string.rocket_height),
                    item.height?.meters,
                    item.height?.feet)
                txt_rocketDiameter.text = String.format(
                    context.getString(string.rocket_diameter),
                    item.diameter?.meters,
                    item.diameter?.feet)
                txt_rocketMass.text = String.format(
                    context.getString(string.rocket_mass),
                    item.mass?.kg)
                txt_firstFlight.text = String.format(
                    context.getString(string.rocket_first_flight),
                    item.flight)
                txt_country.text = String.format(
                    context.getString(string.rocket_country),
                    item.country)
                txt_company.text = String.format(
                    context.getString(string.rocket_company),
                    item.company)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder =
        RocketViewHolder(LayoutInflater.from(parent.context).inflate(layout.item_rocket, parent, false))

    override fun getItemCount(): Int = rockets.size

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        holder.bind(rockets[position])
    }

    fun addDataR(rockets: List<Rocket>) {
        this.rockets.apply {
            clear()
            addAll(rockets)
        }
    }

    private fun formatCost(cost: Int): String {
        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
        val symbols = formatter.decimalFormatSymbols

        symbols.groupingSeparator = ' '
        formatter.decimalFormatSymbols = symbols
        return formatter.format(cost)
    }

}