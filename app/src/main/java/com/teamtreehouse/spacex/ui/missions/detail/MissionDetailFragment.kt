package com.teamtreehouse.spacex.ui.missions.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.teamtreehouse.spacex.R
import com.teamtreehouse.spacex.data.api.ApiHelper
import com.teamtreehouse.spacex.data.api.RetrofitBuilder
import com.teamtreehouse.spacex.data.model.Mission
import com.teamtreehouse.spacex.ui.base.BaseFragment
import com.teamtreehouse.spacex.ui.base.ViewModelFactory
import com.teamtreehouse.spacex.ui.missions.MissionsViewModel
import com.teamtreehouse.spacex.utils.Status
import kotlinx.android.synthetic.main.fragment_mission_detail.*

class MissionDetailFragment : BaseFragment() {

    companion object {
        private const val EXTRA_MISSION = "mission_id"

        fun newInstance(mission_id: String): MissionDetailFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_MISSION, mission_id)
            val fragment = MissionDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        setupObservers()

        val view = inflater.inflate(R.layout.fragment_mission_detail, container, false)

        setupViewModel()
        setupObservers()

        return view.rootView
    }

    private fun setupViewModel() {
        missionsViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MissionsViewModel::class.java)
    }

    private fun setupObservers() {
        val id = arguments!!.getString(EXTRA_MISSION)
        id?.let {
            missionsViewModel.fetchDetailMission(it)
                .observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                layout_detail.visibility = View.VISIBLE
                                hideProgressBar()
                                resource.data?.let {
                                    mission -> retrieveDetailMission(mission)
                                }
                            }
                            Status.ERROR -> {
                                layout_detail.visibility = View.VISIBLE
                                hideProgressBar()
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            }
                            Status.LOADING -> {
                                showProgressBar()
                                layout_detail.visibility = View.GONE
                            }
                        }
                    }
                })
        }
    }

    private fun retrieveDetailMission(mission: Mission) {
        txt_name.text = mission.name
        txt_id.text = String.format(getString(R.string.mission_id), mission.id)
        txt_desc.text = String.format(getString(R.string.m_desc), mission.desc)
        txt_payload.text = String.format(getString(R.string.mission_payload),
            fixedArrayToText(mission.payload_ids.toString()))
        txt_manuf.text = String.format(getString(R.string.mission_manuf),
            fixedArrayToText(mission.manufact.toString()))

        if (mission.wiki!!.isNotEmpty()) {
            txt_wiki.text = String.format(getString(R.string.m_wiki), mission.wiki)
            txt_wiki.setOnClickListener {
                openLink(mission.wiki)
            }
        } else {
            txt_wiki.visibility = View.GONE
        }

        if (mission.web!!.isNotEmpty()) {
            txt_web.text = String.format(getString(R.string.m_web), mission.web)
            txt_web.setOnClickListener {
                openLink(mission.web)
            }
        } else {
            txt_web.visibility = View.GONE
        }

        if (mission.twitter!!.isNotEmpty()) {
            txt_twitter.text = String.format(getString(R.string.m_twitter), mission.twitter)
            txt_twitter.setOnClickListener {
                openLink(mission.twitter)
            }
        } else {
            txt_twitter.visibility = View.GONE
        }
    }

    private fun openLink(url: String?) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(webIntent)
    }

}