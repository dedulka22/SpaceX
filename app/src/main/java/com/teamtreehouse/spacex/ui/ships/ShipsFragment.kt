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

    private var isSortedByAsc: Boolean = false
    private var flag: Boolean = false

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
        setupObservers(isSortedByAsc)
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

        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.sortByYear)
            .setSingleChoiceItems(
                options,
                -1,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        0 -> {
                            onCheckedChanged(dialog as AlertDialog,true)
                        }
                        1 -> {
                            isSortedByAsc = true
                            onCheckedChanged(dialog as AlertDialog,true)
                        }
                    }
                })

        builder.setPositiveButton(
            R.string.OK) { dialog, which ->
            flag = true
            setupObservers(isSortedByAsc)
        }
        builder.setNegativeButton(R.string.Cancel, null)

        val dialog = builder.create()

        dialog.setOnShowListener {
            onCheckedChanged(dialog as AlertDialog,false)
        }

        dialog.show()
    }

    private fun onCheckedChanged(dialog: AlertDialog, enabled: Boolean) {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = enabled
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

    private fun selector(s: Ship): Int? = s.year_built

    private fun setupObservers(isSortedByAsc: Boolean) {
        shipsViewModel.fetchShips().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerview_ship.visibility = View.VISIBLE
                        hideProgressBar()
                        resource.data?.let { ships ->
                            if (flag) {
                                if (isSortedByAsc) {
                                    retrieveList(ships.sortedBy {selector(it)})
                                } else {
                                    retrieveList(ships.sortedByDescending {selector(it)})
                                }
                            } else if (!flag) {
                                retrieveList(ships)
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