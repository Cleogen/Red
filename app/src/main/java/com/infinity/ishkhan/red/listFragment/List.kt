package com.infinity.ishkhan.red.listFragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.infinity.ishkhan.red.R
import com.infinity.ishkhan.red.addFragment.getColorsList
import com.infinity.ishkhan.red.utils.ColorAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*


class List : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        updateRecycler(view)

        // Inflate the layout for this fragment
        return view
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            updateRecycler(view!!)
    }

    private fun updateRecycler(view: View) {
        val colorArray = getColorsList(context!!.filesDir)

        viewAdapter = ColorAdapter(colorArray)
        viewManager = LinearLayoutManager(context)

        recyclerView = view.colorsRecycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
