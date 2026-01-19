package com.example.bokoar.poi
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bokoar.poi.PoiListActivity
import com.example.bokoar.databinding.ActivityPoiDetailBinding
import com.example.bokoar.map.MapActivity


class PoiDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityPoiDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBarDetail.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val poiId = intent.getStringExtra("POI_ID")

        if (poiId == null){
            finish()
            return
        }

        val poi = PoiRepository.getPoiById(this, poiId)
        if (poi == null){
            finish()
            return
        }

        bindPoi(poi)

        binding.btnViewInMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
    private fun bindPoi(poi: PoiDetailContent) {
        binding.topAppBarDetail.title = poi.name
        binding.tvPOIDetailTitle.text = poi.name
        binding.tvPOIDescription.text = poi.fullDescription


        binding.imgPoiHeader.adapter =
            PoiImagePagerAdapter(poi.images)


    }

}