package com.infinity.ishkhan.red


import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.skydoves.colorpickerpreference.ColorEnvelope
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.io.File
import java.lang.Exception

class Add : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        var selectedColorEnvelope = ColorEnvelope(
            android.graphics.Color.WHITE,"FFFFFF", intArrayOf(255,255,255))

        view.colorPicker.setColorListener {
            selectedColorEnvelope = it
            view.selectedColor.setCardBackgroundColor(it.color)
        }

        view.buttonAdd.setOnClickListener {
            val color = Color(selectedColorEnvelope.colorHtml,selectedColorEnvelope.color)

            if (saveColor(color))
                Toast.makeText(context,"Saved",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show()

        }

        view.buttonReset.setOnClickListener {

            selectedColorEnvelope = ColorEnvelope(
                android.graphics.Color.WHITE,"FFFFFF", intArrayOf(255,255,255))

            view.selectedColor.setCardBackgroundColor(android.graphics.Color.WHITE)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.colorPicker.setPaletteDrawable(resources.getDrawable(R.drawable.meganduncanson,resources.newTheme()))
            }else{
                view.colorPicker.setPaletteDrawable(resources.getDrawable(R.drawable.meganduncanson))
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun saveColor(color: Color): Boolean {

        return try {
            File(context!!.filesDir, "theColors")
                .appendText("${color.name}:${color.color}\n")
            true
        }catch (e:Exception){
            false
        }

    }

}
