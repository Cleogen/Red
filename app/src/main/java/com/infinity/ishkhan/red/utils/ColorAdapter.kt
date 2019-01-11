package com.infinity.ishkhan.red.utils

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.infinity.ishkhan.red.R
import kotlinx.android.synthetic.main.colorcard_layout.view.*
import org.json.JSONObject
import java.io.File

class ColorAdapter (private var colors:List<Color>)
    : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var expanded: View? = null

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
            expanded?.buttonDelete?.visibility = View.GONE
            v.buttonDelete.visibility = View.VISIBLE
            expanded = v
            notifyItemChanged(position, 0)
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

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.cardView.buttonDelete.visibility = View.GONE
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        expanded?.buttonDelete?.visibility = View.VISIBLE
    }
}
