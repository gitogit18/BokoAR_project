package com.example.bokoar.poi

data class PoiDetailContent (
    val id: String,
    val name: String,
    val shortDescription: String,
    val fullDescription: String,
    val latitude: Double,
    val longitude: Double,
    val images: List<String>, // R.drawable.xxx
    val category: String, // e.g. "Historical"
)