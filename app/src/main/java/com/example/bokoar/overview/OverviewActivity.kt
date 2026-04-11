package com.example.bokoar.overview

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bokoar.R
import com.example.bokoar.poi.PoiListActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton


class OverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        val btnExplore = findViewById<MaterialButton>(R.id.btnExplore)

        btnExplore.setOnClickListener {
            startActivity(Intent(this, PoiListActivity::class.java))

        }

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBarOverview)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}