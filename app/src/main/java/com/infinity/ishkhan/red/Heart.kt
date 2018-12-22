package com.infinity.ishkhan.red

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_heart.*
import kotlinx.android.synthetic.main.fragment_heart.view.*
import org.json.JSONObject
import java.io.File
import java.util.*

class Heart : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_heart, container, false)

        try {
            val theColors = JSONObject(File(context!!.filesDir, "theColors").readText()).getJSONArray("colors")
            val id = Random().nextInt(theColors.length())
            val color = theColors.getJSONObject(id)
            view.mainCard.setCardBackgroundColor(color.getInt("color"))
            view.colorName.text = color.getString("name")
        }
        catch (e:Exception){
            view.mainCard.setCardBackgroundColor(Color.BLACK)
            view.colorName.text = "Black Pearl"
        }

        return view
    }


}
