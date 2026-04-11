package com.example.bokoar.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.bokoar.R
import com.google.android.material.appbar.MaterialToolbar

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBarSettings)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Profile"
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val references = listOf(
            "Balai Pelestarian Cagar Budaya (BPCB) Yogyakarta",
            "Kementerian Pendidikan dan Kebudayaan RI",
            "UNESCO World Heritage Centre",
            "Mapbox Documentation",
            "Vuforia Engine Documentation",
            "Unity Documentation"
        )
        val tvReferences = findViewById<TextView>(R.id.tvReferences)
        tvReferences.text = references.joinToString("\n\n")

        val credits = listOf(
            "Icons8",
            "Figma"
        )

        val tvCredits = findViewById<TextView>(R.id.tvCredits)
        tvCredits.text = credits.joinToString("\n")

//        findViewById<View>(R.id.rowDevelopment).setOnClickListener {
//
//        }
    }
}
