package com.example.bokoar
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.bokoar.poi.PoiListActivity
import com.google.android.material.card.MaterialCardView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bokoar.map.MapActivity
import com.example.bokoar.settings.SettingsActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val cardPoi = findViewById<MaterialCardView>(R.id.cardPOI)

        cardPoi.setOnClickListener {
            val intent = Intent(this, PoiListActivity::class.java)
            startActivity(intent)
        }

        val howToUseButton = findViewById<MaterialCardView>(R.id.cardHowTo)
        howToUseButton.setOnClickListener {
            startActivity(Intent(this, HowToUseActivity::class.java))
        }


        val tvLogout = findViewById<TextView>(R.id.tvLogout)
        tvLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ -> finishAffinity()}
                .setNegativeButton("Cancel", null)
                .show()
        }

        val settingsButton = findViewById<ImageButton>(R.id.btnSettings)
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        val openMapButton = findViewById<MaterialCardView>(R.id.cardMap)
        openMapButton.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        val startARTour = findViewById<MaterialCardView>(R.id.cardStartAR)
        startARTour.setOnClickListener {
            startActivity(Intent(this, UnityTestActivity::class.java))
        }


    }
}