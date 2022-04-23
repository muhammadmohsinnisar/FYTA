package com.example.fytatest

import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class PostJava : AsyncTask<String?, String?, String>() {
    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onPostExecute(s: String) {
        super.onPostExecute(s)
        println("Response : $s")
    }

    override fun doInBackground(vararg p0: String?): String {
        val urlString = p0[0] // Url POST
        val data = p0[1] //Datos enviados POST
        var response = ""
        var out: OutputStream? = null
        try {
            val url = URL(urlString)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "POST"
            out = BufferedOutputStream(urlConnection.outputStream)
            val writer = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
            writer.write(data)
            writer.flush()
            writer.close()
            out.close()
            urlConnection.connect()
            val responseCode = urlConnection.responseCode
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                var line: String
                val br = BufferedReader(InputStreamReader(urlConnection.inputStream))
                while (br.readLine().also { line = it } != null) {
                    response += line
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
        return response
    }

}