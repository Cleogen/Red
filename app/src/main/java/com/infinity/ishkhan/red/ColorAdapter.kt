package com.infinity.ishkhan.red

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.colorcard_layout.view.*
import org.json.JSONObject
import java.io.File
import kotlin.collections.List

class ColorAdapter (private var colors:List<Color>)
    : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.colorcard_layout,parent,false) as CardView

        context = parent.context
        return ViewHolder(cardView)
    }

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardView.cardColorName.text = colors[position].name
        holder.cardView.setCardBackgroundColor(colors[position].color)
        holder.cardView.setOnClickListener { v->
            if (v.buttonDelete.visibility == View.GONE){
                v.layoutParams.height += 100
                v.buttonDelete.visibility = View.VISIBLE
            }
            else{
                v.layoutParams.height -= 100
                v.buttonDelete.visibility = View.GONE
            }
        }

        holder.cardView.buttonDelete.setOnClickListener {
            colors = colors.take(position) + colors.drop(position + 1)
            val file = File(context.filesDir, "theColors")
            val jsonData = JSONObject(file.readText())
            jsonData.getJSONArray("colors").remove(position)
            file.writeText(jsonData.toString())

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, colors.size)
        }
    }



}
