package com.infinity.ishkhan.red.addFragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.infinity.ishkhan.red.DEFAULT_COLOR
import com.infinity.ishkhan.red.R
import com.infinity.ishkhan.red.utils.Color
import com.infinity.ishkhan.red.utils.getRequest
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class Add : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        var selectedColorEnvelope = DEFAULT_COLOR

        view.colorPicker.setColorListener {
            selectedColorEnvelope = it
            view.selectedColor.setCardBackgroundColor(it.color)
        }

        view.buttonAdd.setOnClickListener {
            val uri = buildURL(selectedColorEnvelope.colorHtml)

            progressBar.visibility = View.VISIBLE

            getRequest(uri) { response ->
                val color = Color(getNameFromJSON(response), selectedColorEnvelope.color)

                Toast.makeText(
                    context,
                    if (saveColor(color, context!!.filesDir)) "${color.name} Saved"
                    else "Failed",
                    Toast.LENGTH_SHORT
                ).show()

                progressBar.visibility = View.GONE
            }

        }

        view.buttonReset.setOnClickListener {
            selectedColorEnvelope = DEFAULT_COLOR
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                view.colorPicker.setPaletteDrawable(resources.getDrawable(R.drawable.meganduncanson,resources.newTheme()))
            else
                view.colorPicker.setPaletteDrawable(resources.getDrawable(R.drawable.meganduncanson))
        }

        view.buttonCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 0)
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val bitmap = data?.extras?.get("data") as Bitmap
        colorPicker.setPaletteDrawable(BitmapDrawable(resources, bitmap))
    }
}