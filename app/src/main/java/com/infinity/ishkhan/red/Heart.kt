package com.infinity.ishkhan.red

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_heart.*
import kotlinx.android.synthetic.main.fragment_heart.view.*
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
            val theColors = File(context!!.filesDir, "theColors").readLines()
            val id = Random().nextInt(theColors.size)
            val color = theColors[id].split(":")
            view.mainCard.setCardBackgroundColor(color[1].toInt())
            view.colorName.text = color[0]
        }
        catch (e:Exception){
            view.mainCard.setCardBackgroundColor(Color.BLACK)
            view.colorName.text = "Black Pearl"
        }

        return view
    }


}
