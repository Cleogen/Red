package com.infinity.ishkhan.red.utils

import android.net.Uri
import android.os.AsyncTask
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class NetworkCall(private val response: AsyncResponse) : AsyncTask<String, Int, JSONObject>() {

    interface AsyncResponse{
        fun onFinish(response: JSONObject?)
    }

    override fun doInBackground(vararg params: String?): JSONObject {
        val url = URL(params[0])

        var connection: HttpURLConnection? = null
        var reader:BufferedReader? = null

        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.connect()

            val stream = connection.inputStream
            reader = BufferedReader(InputStreamReader(stream))
            val buffer = StringBuffer()
            var line:String? = ""

            while (line != null){
                line = reader.readLine()
                buffer.append("$line\n")
            }
            return JSONObject(buffer.toString())

        }catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e:IOException) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            try {
                reader?.close()
            } catch (e:IOException) {
                e.printStackTrace()
            }
        }
        return JSONObject()
    }

    override fun onPostExecute(result: JSONObject?) {
        super.onPostExecute(result)
        response.onFinish(result)
    }

}

fun getRequest(uri: Uri, callback: ((JSONObject?) -> Unit)) {
    NetworkCall(object :
        NetworkCall.AsyncResponse {
        override fun onFinish(response: JSONObject?) {
            callback(response)
        }
    }).execute(uri.toString())
}
