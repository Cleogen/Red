package com.infinity.ishkhan.red

import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import android.os.AsyncTask


class NetworkCall(private val response:AsyncResponse) : AsyncTask<String, Int, JSONObject>() {

    interface AsyncResponse{
        fun onFinish(out: Boolean,response: JSONObject?)
    }

    override fun doInBackground(vararg params: String?): JSONObject {
        val url = URL(params[0])

        var connection: HttpURLConnection? = null
        var reader:BufferedReader? = null

        try {
            connection = url.openConnection() as HttpURLConnection
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
        response.onFinish(true,result)
    }
}
