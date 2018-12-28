package com.infinity.ishkhan.red.addFragment

import android.net.Uri
import com.infinity.ishkhan.red.*
import com.infinity.ishkhan.red.utils.Color
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException

fun buildURL(colorHex: String): Uri = Uri.Builder().scheme(NETWORK_CALL_SCHEME)
    .authority(COLOR_API_DOMAIN)
    .appendPath("v1")
    .appendPath(colorHex).build()

fun getNameFromJSON(response: JSONObject?): String = response!!.getJSONArray(COLOR_IN_JSON)
    .getJSONObject(0)
    .getString(COLOR_NAME_JSON)

fun saveColor(color: Color, dir: File?): Boolean = try {
    val file = File(dir, COLORS_FILE_NAME)
    val jsonData = JSONObject(file.readText())
    jsonData.getJSONArray(COLOR_IN_JSON)
        .put(
            JSONObject().put(COLOR_NAME_JSON, color.name)
                .put(COLOR_VALUE_JSON, color.color)
        )
    file.writeText(jsonData.toString())
    true
} catch (e: FileNotFoundException) {
    val file = File(dir, COLORS_FILE_NAME)
    val jsonData = JSONObject().put(
        COLOR_IN_JSON,
        JSONArray().put(
            JSONObject()
                .put(COLOR_NAME_JSON, color.name)
                .put(COLOR_VALUE_JSON, color.color)
        )
    )
    file.writeText(jsonData.toString())
    true
} catch (e: Exception) {
    false
}

fun getColorsList(dir: File?): List<Color> = try {
    val theColors = JSONObject(File(dir, COLORS_FILE_NAME).readText()).getJSONArray(COLOR_IN_JSON)
    var color: JSONObject
    List(theColors.length()) {
        color = theColors.getJSONObject(it)
        Color(color.getString(COLOR_NAME_JSON), color.getInt(COLOR_VALUE_JSON))
    }

} catch (e: Exception) {
    arrayListOf()
}