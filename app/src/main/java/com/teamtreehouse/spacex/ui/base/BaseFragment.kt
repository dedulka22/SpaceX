package com.teamtreehouse.spacex.ui.base

import android.R
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


open class BaseFragment : Fragment() {
    private lateinit var rootView: View
    private var progressBar: ProgressBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
        initProgressBar()
    }

    private fun initProgressBar() {
        val layout = rootView as ViewGroup
        progressBar = ProgressBar(rootView.context, null, android.R.attr.progressBarStyleLarge)
        progressBar?.isIndeterminate = true

        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        val rl = RelativeLayout(rootView.context)

        rl.gravity = Gravity.CENTER
        rl.addView(progressBar)

        layout.addView(rl, params)
        hideProgressBar()
    }

    fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar?.visibility = View.GONE
    }

    protected fun fixedArrayToText(text: String): String {
        return text.replace("[", "").replace("]", "")
    }
}