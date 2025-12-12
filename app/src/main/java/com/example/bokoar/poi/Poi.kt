package com.example.bokoar.poi

data class Poi (
    val id: Int,
    val title: String,
    val subtitle: String,
    val imageRes: Int, // R.drawable.xxx
    val category: String // e.g. "Historical"
)