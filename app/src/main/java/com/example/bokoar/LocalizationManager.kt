package com.example.bokoar

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader

object LocalizationManager {

    private lateinit var  jsonArray: JSONArray

    fun init(context: Context) {
        val deviceLanguage = context.resources.configuration.locales.get(0).language

        val fileName = if (deviceLanguage == "id"){
            "assets/poi_detailsID.json"
        } else {
            "assets/poi_details.json"
        }
        Log.d("LocalizationManager", "Reading file: $deviceLanguage")
        Log.d("LocalizationManager", "Reading file: $fileName")



        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val jsonString = String(buffer, Charsets.UTF_8)
        jsonArray = JSONArray(jsonString)
    }

    fun getPOIById(id: String): JSONObject? {
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            if (obj.getString("id") == id) {
                return obj
            }
        }
        return null
    }




}