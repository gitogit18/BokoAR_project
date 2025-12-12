package com.example.bokoar.poi
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bokoar.poi.PoiListActivity
import com.example.bokoar.databinding.ActivityPoiDetailBinding


class PoiDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityPoiDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBarDetail.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val title = intent.getStringExtra("poi_title") ?: ""
        val subtitle = intent.getStringExtra("poi_subtitle") ?: ""
        val image = intent.getIntExtra("poi_image", 0)

        binding.topAppBarDetail.title = title
         onBackPressedDispatcher.onBackPressed()
    }
}