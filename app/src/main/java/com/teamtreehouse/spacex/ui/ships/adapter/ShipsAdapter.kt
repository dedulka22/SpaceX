package com.teamtreehouse.spacex.ui.ships.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamtreehouse.spacex.R
import com.teamtreehouse.spacex.data.model.Ship
import kotlinx.android.synthetic.main.item_ship.view.*

class ShipsAdapter(private var ships: ArrayList<Ship>)
    : RecyclerView.Adapter<ShipsAdapter.ShipViewHolder>() {

    inner class ShipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Ship) {
            itemView.apply {
                item.image?.let {
                    if (it.isNotEmpty()) {
                        img_pic.visibility = View.VISIBLE
                        Picasso.get().load(item.image).into(img_pic)
                    }
                }
                txt_name.text = item.name
                txt_id.text = String.format(
                    context.getString(R.string.mission_id),
                    item.id)
                item.role?.forEach {
                    if (it.isNotEmpty()) {
                        txt_role.text = String.format(
                            context.getString(R.string.ship_role),
                            fixedArrayToText(item.role.toString())
                        )
                    } else
                        txt_role.visibility = View.GONE
                }
                item.year_built?.let {
                    if (it != null) {
                        txt_year_built.visibility = View.VISIBLE
                        txt_year_built.text = String.format(
                            context.getString(R.string.ship_year_built),
                            item.year_built)
                    }
                }

                txt_ship_type.text = String.format(
                    context.getString(R.string.ship_type),
                    item.ship_type)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipViewHolder =
        ShipViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ship, parent, false))

    override fun getItemCount(): Int = ships.size

    override fun onBindViewHolder(holder: ShipViewHolder, position: Int) {
        holder.bind(ships[position])
    }

    fun addDataS(ships: List<Ship>) {
        this.ships.apply {
            clear()
            addAll(ships)
        }
    }

    private fun fixedArrayToText(text: String): String {
        return text.replace("[", "").replace("]", "")
    }
}