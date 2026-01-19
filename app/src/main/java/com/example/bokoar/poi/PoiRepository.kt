package com.example.bokoar.poi

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader


object PoiRepository {
    private var cachedPoiList: List<PoiDetailContent>? = null

    private fun load(context: Context){
        if (cachedPoiList !=null) return

        val inputStream = context.assets.open("poi_details.json")
        val reader = InputStreamReader(inputStream)

        val type = object : TypeToken<List<PoiDetailContent>>() {}.type
        cachedPoiList = Gson().fromJson(reader, type)

        reader.close()
    }

    fun getPoiById(context: Context, id: String): PoiDetailContent?{
        load(context)
        return cachedPoiList?.find { it.id == id }
    }

    fun getAllPois(context: Context): List<PoiDetailContent> {
        load(context)
        return cachedPoiList ?: emptyList()
    }
}
