package com.example.bokoar.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bokoar.R
import com.example.bokoar.poi.PoiDetailActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
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

        val ratuBoko = Point.fromLngLat(110.488704, -7.770369)

        poiList = loadPoimarkersFromJSon()
        val selectedPoiId = intent.getStringExtra("POI_ID")
        val selectedPoi = poiList.find { it.id == selectedPoiId }



        // Load style dulu, baru set camera
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { style ->
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .center(
                        if (selectedPoi != null)
                            Point.fromLngLat(selectedPoi.longitude, selectedPoi.latitude)
                        else
                            ratuBoko
                    )
                    .zoom(if (selectedPoi != null) 17.5 else 15.0)
                    .build()
            )

            if (selectedPoi != null) {
                Toast.makeText(this, "Showing ${selectedPoi.name}", Toast.LENGTH_SHORT).show()
            }

            val polygon = listOf(
                Point.fromLngLat(110.487552, -7.769333),
                Point.fromLngLat(110.488645, -7.768733),
                Point.fromLngLat(110.489665, -7.768786),
                Point.fromLngLat(110.489600, -7.769530),
                Point.fromLngLat(110.490920, -7.769573),
                Point.fromLngLat(110.491210, -7.771204),
                Point.fromLngLat(110.491762, -7.771837),
                Point.fromLngLat(110.491730, -7.772294),
                Point.fromLngLat(110.489316, -7.771821),
                Point.fromLngLat(110.489375, -7.770832),
                Point.fromLngLat(110.488114, -7.770545),
                Point.fromLngLat(110.488109, -7.769440),
                Point.fromLngLat(110.487552, -7.769333) // tutup polygon
            )


            val polygonFeature = Feature.fromGeometry(
                Polygon.fromLngLats(listOf(polygon))
            )

            val geoJsonSource = geoJsonSource("ratu-boko-source") {
                feature(polygonFeature)
            }

            style.addSource(geoJsonSource)

            val fillLayer = fillLayer("ratu-boko-fill", "ratu-boko-source") {
                fillColor("#8B4513")
                fillOpacity(0.1)
            }

            style.addLayer(fillLayer)

            val lineLayer = lineLayer("ratu-boko-outline", "ratu-boko-source") {
                lineColor("#5A2D0C")
                lineWidth(2.0)
            }

            style.addLayer(lineLayer)

            addPoiIcon(style)
            setupPoiAnnotations()
            mapView.getMapboxMap().addOnMapClickListener { point ->

                if (isPointInPolygon(point, polygon)) {
                    Toast.makeText(this, "\"You are exploring Ratu Boko Heritage Site\"", Toast.LENGTH_SHORT).show()
                }

                true
            }

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
                .withIconSize(
                    if (poi.id == intent.getStringExtra("POI_ID")) 0.9 else 0.6)
                .withData(
                    Gson().toJsonTree(
                        mapOf("id" to poi.id)
                    )
                )

            pointAnnotationManager.create(options)
        }

        pointAnnotationManager.addClickListener { annotation ->

            // Reset all markers to normal size
            pointAnnotationManager.annotations.forEach {
                it.iconSize = 0.6
                pointAnnotationManager.update(it)
            }

            // Enlarge selected marker
            annotation.iconSize = 0.9
            pointAnnotationManager.update(annotation)


            val poiId = annotation.getData()
                ?.asJsonObject
                ?.get("id")
                ?.asString

            Log.d("POI_CLICK", "Clicked: $poiId")

            if (poiId != null) {

                val poi = poiList.find { it.id == poiId }

                com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                    .setTitle(poi?.name ?: "POI")
                    .setMessage("Explore this location?")

                    .setNegativeButton("Cancel") { _, _ ->
                        annotation.iconSize = 0.6
                        pointAnnotationManager.update(annotation)
                    }

                    .setPositiveButton("View") { _, _ ->
                        openPoiDetail(poiId)
                    }

                    .setOnDismissListener {
                        annotation.iconSize = 0.6
                        pointAnnotationManager.update(annotation)
                    }

                    .show()
            }

            true

        }
    }

    private fun openPoiDetail(poiId: String) {
        val intent = Intent(this, PoiDetailActivity::class.java)
        intent.putExtra("POI_ID", poiId)
        startActivity(intent)
    }

    fun isPointInPolygon(point: Point, polygon: List<Point>): Boolean {
        var intersectCount = 0

        for (i in polygon.indices) {
            val j = (i + 1) % polygon.size

            val xi = polygon[i].longitude()
            val yi = polygon[i].latitude()
            val xj = polygon[j].longitude()
            val yj = polygon[j].latitude()

            val intersect = ((yi > point.latitude()) != (yj > point.latitude())) &&
                    (point.longitude() < (xj - xi) * (point.latitude() - yi) / (yj - yi + 1e-10) + xi)

            if (intersect) intersectCount++
        }

        return intersectCount % 2 == 1
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


