package com.camayla.githubkotlin

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list_user.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class ListUserActivity : AppCompatActivity() {

    var listUserArr: ArrayList<String> = ArrayList()
    var adapterListUser: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)

        var intent = intent
        val userName: String = intent.getStringExtra("data")

        val listUserUrl : String = "https://api.github.com/search/users?q=${userName}"


//        lbl_list_user.text = url
        GetListUser().execute(listUserUrl)

        adapterListUser = ArrayAdapter(this, android.R.layout.simple_list_item_1, listUserArr)
        listview_userlist.adapter = adapterListUser
    }

    inner  class GetListUser: AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            var content: StringBuilder = StringBuilder()
            val url: URL = URL(params[0])
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStreamReader: InputStreamReader = InputStreamReader(urlConnection.inputStream)
            val bufferedReader: BufferedReader = BufferedReader((inputStreamReader))

            var line: String = ""

            try {
                do {
                    line = bufferedReader.readLine()
                    if (line != null) {
                        content.append(line)
                    }
                }while (line != null)
                bufferedReader.close()
            }catch (e: Exception) {
                Log.d("ERROR", e.toString())
            }
            return content.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            val usersInfo: JSONObject = JSONObject(result)

            val listUser: JSONArray = usersInfo.getJSONArray("items")

            val userLength : Int = listUser.length()
            var htmlUrl: String = ""
            for (user in 0 until userLength) {
                var user: JSONObject = listUser.getJSONObject(user)
                htmlUrl = user.getString("html_url")
                listUserArr.add(htmlUrl)
            }
            adapterListUser?.notifyDataSetChanged()
        }
    }
}
