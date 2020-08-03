package com.teamtreehouse.spacex.ui.ships

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamtreehouse.spacex.R
import com.teamtreehouse.spacex.data.api.ApiHelper
import com.teamtreehouse.spacex.data.api.RetrofitBuilder
import com.teamtreehouse.spacex.data.model.Ship
import com.teamtreehouse.spacex.ui.base.BaseFragment
import com.teamtreehouse.spacex.ui.base.ViewModelFactory
import com.teamtreehouse.spacex.ui.ships.adapter.ShipsAdapter
import com.teamtreehouse.spacex.utils.Status
import kotlinx.android.synthetic.main.fragment_ships.*

class ShipsFragment : BaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ships, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        shipsViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(ShipsViewModel::class.java)
    }

    private fun setupUI() {
        recyclerview_ship.layoutManager = LinearLayoutManager(context)
        adapterS = ShipsAdapter(arrayListOf())
        recyclerview_ship.addItemDecoration(
            DividerItemDecoration(
                recyclerview_ship.context,
                (recyclerview_ship.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerview_ship.adapter = adapterS
    }

    private fun setupObservers() {
        shipsViewModel.fetchShips().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerview_ship.visibility = View.VISIBLE
                        hideProgressBar()
                        resource.data?.let {
                                ships -> retrieveList(ships) }
                    }
                    Status.ERROR -> {
                        recyclerview_ship.visibility = View.VISIBLE
                        hideProgressBar()
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        showProgressBar()
                        recyclerview_ship.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(ship: List<Ship>) {
        adapterS.apply {
            addDataS(ship)
            notifyDataSetChanged()
        }
    }
}