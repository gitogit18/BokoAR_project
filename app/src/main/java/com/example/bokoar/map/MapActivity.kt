package com.example.bokoar.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bokoar.R
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mapbox.maps.plugin.locationcomponent.location

class MapActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private val REQ_LOCATION = 1001

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
            // enable user location
            if (hasLocationPermission()) {
                enableUserLocation()
            } else {
                requestLocationPermission()
            }
        }
    }

    private fun hasLocationPermission(): Boolean{
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun requestLocationPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQ_LOCATION
        )
    }

    private fun enableUserLocation(){
        mapView.location.updateSettings {
            enabled = true
            pulsingEnabled = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQ_LOCATION && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            enableUserLocation()
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
