package com.infinity.ishkhan.red

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.colorcard_layout.view.*
import java.io.File
import kotlin.collections.List

class ColorAdapter (private var colors:List<Color>)
    : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.colorcard_layout,parent,false) as CardView

        return ViewHolder(cardView)
    }

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardView.cardColorName.text = colors[position].name
        holder.cardView.setCardBackgroundColor(colors[position].color)
        holder.cardView.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                v.layoutParams.height = 100
                v.buttonDelete.visibility = View.VISIBLE
            }
            else{
                v.layoutParams.height = 65
                v.buttonDelete.visibility = View.GONE
            }
            notifyItemChanged(position)
        }

        holder.cardView.buttonDelete.setOnClickListener {
            val file = File(com.infinity.ishkhan.red.List().context!!.filesDir,"theColors")
            var lines = file.readLines()
            lines = lines.take(position - 1 ) + lines.drop(position)
            file.writeText(lines.joinToString(System.lineSeparator()))
            colors = colors.take(position - 1 ) + colors.drop(position)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, colors.size)
        }
    }



}
