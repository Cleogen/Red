package com.infinity.ishkhan.red


import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.skydoves.colorpickerpreference.ColorEnvelope
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.io.File
import java.lang.Exception
import android.net.Uri
import kotlinx.android.synthetic.main.fragment_add.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.FileNotFoundException


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
//https://api.color.pizza/v1/
        view.buttonAdd.setOnClickListener {
            val uri = Uri.Builder().scheme("http")
                .authority("api.color.pizza")
                .appendPath("v1")
                .appendPath(selectedColorEnvelope.colorHtml)
                .build().toString()

            progressBar.visibility = View.VISIBLE

            NetworkCall(object :NetworkCall.AsyncResponse{
                override fun onFinish(out: Boolean, response:JSONObject?) {
                    val data = response!!.getJSONArray("colors").getJSONObject(0)
                    val name = data.getString("name")
                    val color = Color(name,selectedColorEnvelope.color)
                    if (saveColor(color))
                        Toast.makeText(context, "$name Saved", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }).execute(uri)

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
            val file = File(context!!.filesDir, "theColors")
            val jsonData = JSONObject(file.readText())
            jsonData.getJSONArray("colors")
                .put(JSONObject().put("name",color.name).put("color",color.color))
            file.writeText(jsonData.toString())
            true
        }catch (e:FileNotFoundException){
            val file = File(context!!.filesDir, "theColors")
            val jsonData = JSONObject().put("colors",
                JSONArray().put(JSONObject().put("name",color.name).put("color",color.color)))
            file.writeText(jsonData.toString())
            true
        }catch (e:Exception){
            false
        }

    }

}