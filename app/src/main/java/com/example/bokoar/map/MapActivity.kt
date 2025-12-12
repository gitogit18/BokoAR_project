package com.example.bokoar.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bokoar.R
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

class MapActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBarMap)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        mapView = findViewById(R.id.mapView)

        val ratuBoko = Point.fromLngLat(110.4923, -7.7696)

        // Load style dulu, baru set camera
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .center(ratuBoko)
                    .zoom(14.0)
                    .build()
            )
        }
    }

    override fun onStart() {
        super.onStart();
        mapView.onStart()
    }
    override fun onStop() {
        super.onStop();
        mapView.onStop()
    }
    override fun onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory()
    }
    override fun onDestroy() {
        super.onDestroy();
        mapView.onDestroy()
    }
}
