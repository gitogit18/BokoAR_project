package com.example.bokoar.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bokoar.R
import com.example.bokoar.poi.PoiDetailActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location
import java.io.InputStreamReader


class MapActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private val REQ_LOCATION = 1001

    private lateinit var poiList: List<PoiMarker>

    private fun loadPoimarkersFromJSon(): List<PoiMarker> {
        val inputStream = resources.openRawResource(R.raw.poi_marker)
        val reader = InputStreamReader(inputStream)

        val type = object : TypeToken<List<PoiMarker>>() {}.type
        return Gson().fromJson(reader, type)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val toolbar =
            findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBarMap)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        mapView = findViewById(R.id.mapView)

        val ratuBoko = Point.fromLngLat(110.4923, -7.7696)

        poiList = loadPoimarkersFromJSon()

        // Load style dulu, baru set camera
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { style ->
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .center(ratuBoko)
                    .zoom(15.0)
                    .build()
            )

            addPoiIcon(style)
            setupPoiAnnotations()

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

    private fun addPoiIcon(style: Style) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.markerpeta)
        style.addImage("poi-icon", bitmap)
    }

    private fun setupPoiAnnotations() {

        val annotationApi = mapView.annotations
        val pointAnnotationManager =
            annotationApi.createPointAnnotationManager()

        poiList.forEach { poi ->
            val options = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(poi.longitude, poi.latitude))
                .withIconImage("poi-icon")
                .withIconSize(0.6)
                .withData(
                    Gson().toJsonTree(
                        mapOf("id" to poi.id)
                    )
                )

            pointAnnotationManager.create(options)
        }

        pointAnnotationManager.addClickListener { annotation ->

            val poiId = annotation.getData()
                ?.asJsonObject
                ?.get("id")
                ?.asString

            Log.d("POI_CLICK", "Clicked: $poiId")

            if (poiId != null) {
                openPoiDetail(poiId)
            }

            true

        }
    }

    private fun openPoiDetail(poiId: String) {
        val intent = Intent(this, PoiDetailActivity::class.java)
        intent.putExtra("POI_ID", poiId)
        startActivity(intent)
    }




    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}


