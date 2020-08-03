package com.teamtreehouse.spacex.ui.rockets

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
import com.teamtreehouse.spacex.data.model.Rocket
import com.teamtreehouse.spacex.ui.base.BaseFragment
import com.teamtreehouse.spacex.ui.base.ViewModelFactory
import com.teamtreehouse.spacex.ui.rockets.adapter.RocketsAdapter
import com.teamtreehouse.spacex.utils.Status
import kotlinx.android.synthetic.main.fragment_rockets.*

class RocketsFragment : BaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rockets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        rocketsViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(RocketsViewModel::class.java)
    }

    private fun setupUI() {
        recyclerview_rocket.layoutManager = LinearLayoutManager(context)
        adapterR = RocketsAdapter(arrayListOf())
        recyclerview_rocket.addItemDecoration(
            DividerItemDecoration(
                recyclerview_rocket.context,
                (recyclerview_rocket.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerview_rocket.adapter = adapterR
    }

    private fun setupObservers() {
        rocketsViewModel.fetchRockets().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerview_rocket.visibility = View.VISIBLE
                        hideProgressBar()
                        resource.data?.let {
                                rockets -> retrieveList(rockets) }
                    }
                    Status.ERROR -> {
                        recyclerview_rocket.visibility = View.VISIBLE
                        hideProgressBar()
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        showProgressBar()
                        recyclerview_rocket.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(rocket: List<Rocket>) {
        adapterR.apply {
            addDataR(rocket)
            notifyDataSetChanged()
        }
    }
}