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
        super.onCreate(savedInstanceState)
        binding = ActivityPoiListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol back di toolbar
        binding.topAppBarPOI.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        // TODO: ganti sample data ini dengan data beneran
        val recommended = listOf(
            Poi(1, "Ratu Boko Temple", "Most Popular", R.drawable.ratubokogate, "Recommended"),
            Poi(id = 2, "Cave Temple", "Meditation chambers", R.drawable.ratubokogate, "Recommended"),
            Poi(3, "Bathing Pool", "Sacred water complex", R.drawable.ratubokogate, "Recommended")
        )

        val sacredSites = listOf(
            Poi(2, "Cave Temple", "Meditation chambers", R.drawable.ratubokogate, "Sacred Sites"),
            Poi(3, "Bathing Pool", "Sacred water complex", R.drawable.ratubokogate, "Sacred Sites")
        )

        val historical = listOf(
            Poi(4, "Stone Terrace", "Panoramic viewpoint", R.drawable.ratubokogate, "Historical Structures"),
            Poi(5, "Guard Tower", "Defensive lookout", R.drawable.ratubokogate, "Historical Structures")
        )

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

        // SACRED SITES – vertical
        binding.rvSacredSites.apply {
            layoutManager = LinearLayoutManager(this@PoiListActivity)
            adapter = PoiListAdapter(sacredSites) { poi ->
                openPoiDetail(poi)
            }
        }

        // HISTORICAL STRUCTURES – vertical
        binding.rvHistorical.apply {
            layoutManager = LinearLayoutManager(this@PoiListActivity)
            adapter = PoiListAdapter(historical) { poi ->
                openPoiDetail(poi)
            }
        }
    }

    private fun openPoiDetail(poi: Poi) {
        val intent = Intent(this, PoiDetailActivity::class.java).apply {
            putExtra("poi_title", poi.title)
            putExtra("poi_subtitle", poi.subtitle)
            putExtra("poi_image", poi.imageRes)
            // Kalau mau: putExtra("poi_description", longText)
        }
        startActivity(intent)
    }
}
