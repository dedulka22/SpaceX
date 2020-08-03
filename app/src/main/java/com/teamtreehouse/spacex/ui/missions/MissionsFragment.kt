package com.teamtreehouse.spacex.ui.missions

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
import com.teamtreehouse.spacex.data.model.Mission
import com.teamtreehouse.spacex.ui.base.BaseFragment
import com.teamtreehouse.spacex.ui.base.ViewModelFactory
import com.teamtreehouse.spacex.ui.missions.adapter.MissionsAdapter
import com.teamtreehouse.spacex.ui.missions.adapter.OnMissionItemClicked
import com.teamtreehouse.spacex.ui.missions.detail.MissionDetailFragment
import com.teamtreehouse.spacex.utils.Status
import kotlinx.android.synthetic.main.fragment_missions.*

class MissionsFragment : BaseFragment(), OnMissionItemClicked {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_missions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        missionsViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MissionsViewModel::class.java)
    }

    private fun setupUI() {
        recyclerview_mission.layoutManager = LinearLayoutManager(context)
        adapterM = MissionsAdapter(arrayListOf())
        recyclerview_mission.addItemDecoration(
            DividerItemDecoration(
                recyclerview_mission.context,
                (recyclerview_mission.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerview_mission.adapter = adapterM
    }

    private fun setupObservers() {
        missionsViewModel.fetchMissions().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerview_mission.visibility = View.VISIBLE
                        hideProgressBar()
                        resource.data?.let {
                                missions -> retrieveList(missions) }
                    }
                    Status.ERROR -> {
                        recyclerview_mission.visibility = View.VISIBLE
                        hideProgressBar()
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        showProgressBar()
                        recyclerview_mission.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(mission: List<Mission>) {
        adapterM.apply {
            addDataM(mission)
            notifyDataSetChanged()
        }
    }

    override fun onMissionItemClicked(mission_id: String) {
        MissionDetailFragment.newInstance(mission_id)
    }
}