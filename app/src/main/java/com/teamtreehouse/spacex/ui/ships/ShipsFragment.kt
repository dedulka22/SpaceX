package com.teamtreehouse.spacex.ui.ships

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
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

    private var sortByAsc: Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_ships, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers(sortByAsc)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_ship_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                showSortDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSortDialog() {
        val options  = arrayOf("Descending", "Ascending")
        var enabled: Boolean = false

        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.sortByYear)
            .setSingleChoiceItems(
                options,
                -1,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        0 -> {
                            sortByAsc = false
                            enabled = true
                            }
                        1 -> {
                            sortByAsc = true
                            enabled = true
                            }
                    }
                })


        builder.setPositiveButton(
            R.string.OK
        ) { dialog, which ->
            setupObservers(sortByAsc)
        }
        builder.setNegativeButton(R.string.Cancel, null)

        val dialog = builder.create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = enabled
        }

        dialog.show()
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

    private fun setupObservers(sortByAsc: Boolean) {
        shipsViewModel.fetchShips().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerview_ship.visibility = View.VISIBLE
                        hideProgressBar()
                        resource.data?.let { ships ->
                            if (sortByAsc) {
                                retrieveList(ships.sortedBy { item -> item.year_built })
                            } else {
                                retrieveList(ships.sortedByDescending { item -> item.year_built })
                            }
                        }
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