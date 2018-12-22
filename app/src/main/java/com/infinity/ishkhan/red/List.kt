package com.infinity.ishkhan.red


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.lang.Exception
import kotlin.collections.List


class List : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        val colorArray:List<Color>

        colorArray = try {
            val theColors = JSONObject(File(context!!.filesDir, "theColors").readText()).getJSONArray("colors")
            var color:JSONObject
            List(theColors.length()){
                color = theColors.getJSONObject(it)
                Color(color.getString("name"),color.getInt("color"))
            }

        }catch (e:Exception){
            arrayListOf(Color("000000",android.graphics.Color.BLACK))
        }

        viewAdapter = ColorAdapter(colorArray)
        viewManager = LinearLayoutManager(context)

        recyclerView = view.colorsRecycler.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }


        // Inflate the layout for this fragment
        return view
    }

}
