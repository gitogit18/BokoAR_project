package com.example.bokoar.poi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bokoar.R
import com.example.bokoar.databinding.ActivityPoiListBinding

class PoiListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPoiListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val allPois = PoiRepository.getAllPois(this)
        val recommended = allPois.take(3)
        val sacredSites = allPois.filter {
            it.category == "Sacred Sites"
        }
        val historical = allPois.filter {
            it.category == "Historical Structures"
        }


        super.onCreate(savedInstanceState)
        binding = ActivityPoiListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol back di toolbar
        binding.topAppBarPOI.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // RECOMMENDED – horizontal
        binding.rvRecommended.apply {
            layoutManager = LinearLayoutManager(
                this@PoiListActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = RecommendedAdapter(recommended) { poi ->
                openPoiDetail(poi)
            }
        }

//        // SACRED SITES – vertical
//        binding.rvSacredSites.apply {
//            layoutManager = LinearLayoutManager(this@PoiListActivity)
//            adapter = PoiListAdapter(sacredSites) { poi ->
//                openPoiDetail(poi)
//            }
//        }

        // OTHER STRUCTURES – vertical
        binding.rvHistorical.apply {
            layoutManager = LinearLayoutManager(this@PoiListActivity)
            adapter = PoiListAdapter(historical) { poi ->
                openPoiDetail(poi)
            }
        }
    }

    private fun openPoiDetail(poi: PoiDetailContent) {
        val intent = Intent(this, PoiDetailActivity::class.java)
            intent.putExtra("POI_ID", poi.id)
            startActivity(intent)
    }
}
