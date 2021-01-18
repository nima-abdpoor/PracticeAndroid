package com.nima.practice

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager
import com.mapbox.mapboxsdk.style.layers.Layer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback , PermissionsListener {
    var locationlan = 35.678494
    var locationlong = 51.390880

    private val SOURCE_ID = "SOURCE_ID"
    private val ICON_ID = "ICON_ID"
    private val LAYER_ID = "LAYER_ID"
    private lateinit var manager: LocationManager
    private var mapView: MapView? = null
    private val DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID"
    private var mapboxMap: MapboxMap? = null
    private var hoveringMarker: ImageView? = null
    private var view : ImageView? = null
    private lateinit var markerViewManager : MarkerViewManager
    private var permissionsManager: PermissionsManager? = null
    private var droppedMarkerLayer: Layer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
        mapView = findViewById(R.id.mapView)
        view = findViewById(R.id.imageView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        mapView?.removeView(view)
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun bases(){
        val point = LatLng(35.678494, 51.390880)
        val point2 = LatLng(35.678494, 51.390880)
        val point3 = LatLng(35.678494, 51.390880)
        val point4 = LatLng(35.678494, 51.390880)
        val list = ArrayList<LatLng>()
        list.add(point)
        list.add(point2)
        list.add(point3)
        list.add(point4)
        val icon = BitmapFactory.decodeResource(
            resources, R.drawable.mapbox_marker_icon_default
        )

        view?.setImageBitmap(icon)
        btn_choseLocationF_seeBasig.setOnClickListener {
            if (view?.parent != null) {
                (view?.parent as ViewGroup).removeView(view)
            }
            mark(list)
        }
    }

    private fun mark(list: java.util.ArrayList<LatLng>) {
        for (l in list){
            if (view?.parent != null) {
                (view?.parent as ViewGroup).removeView(view)
            }
            markerViewManager = MarkerViewManager(mapView, mapboxMap)
            mapView?.getMapAsync { mapboxMap ->
                view?.let {
                    val marker = MarkerView(l, it)
                    markerViewManager.addMarker(marker)
                }
            }
        }

    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        val lat = LatLng()
        this.mapboxMap = mapboxMap
        bases()

        create()
        val point1 = LatLng(35.678494, 51.390880)
        val point2 = LatLng(36.678494, 52.390880)
        val list = ArrayList<LatLng>()
        list.add(point1)
        list.add(point2)
    }

    private fun create() {
        val symbolLayerIconFeatureList: MutableList<Feature> = ArrayList()
        symbolLayerIconFeatureList.add(
            Feature.fromGeometry(
                Point.fromLngLat(36.00, 52.00)
            )
        )
        symbolLayerIconFeatureList.add(
            Feature.fromGeometry(
                Point.fromLngLat(37.00, 51.390855)
            )
        )
        symbolLayerIconFeatureList.add(
            Feature.fromGeometry(
                Point.fromLngLat(35.678494, 51.3999)
            )
        )
        mapboxMap?.setStyle(
            Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
                .withImage(
                    ICON_ID,
                    BitmapFactory.decodeResource(resources, R.drawable.mapbox_logo_icon)
                )
                .withSource(
                    GeoJsonSource(
                        SOURCE_ID,
                        FeatureCollection.fromFeatures(symbolLayerIconFeatureList)
                    )
                ) // Adding the actual SymbolLayer to the map style. An offset is added that the bottom of the red
                // marker icon gets fixed to the coordinate, rather than the middle of the icon being fixed to
                // the coordinate point. This is offset is not always needed and is dependent on the image
                // that you use for the SymbolLayer icon.
                .withLayer(
                    SymbolLayer(LAYER_ID, SOURCE_ID)
                        .withProperties(
                            iconImage(ICON_ID),
                            iconAllowOverlap(true),
                            iconIgnorePlacement(true)
                        )
                )
        ) { style ->
            enableCurrentLocation(style)

            //remove logo
            mapboxMap?.uiSettings?.isAttributionEnabled = false;
            mapboxMap?.uiSettings?.isLogoEnabled = false;

            //change my marker drawable
            hoveringMarker = ImageView(this)
            hoveringMarker?.setBackgroundResource(R.drawable.ic_placeholder)


            fab_choseLocation.setOnClickListener {


                //if gps Not Active
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                } else {
                    enableCurrentLocation(style)
                    //route current location
                    val position = CameraPosition.Builder()
                        .target(LatLng(locationlan, locationlong))
                        .zoom(17.0)
//                    .bearing(180.0)
                        .tilt(30.0)
                        .build()

                    //camera update
                    mapboxMap?.animateCamera(
                        CameraUpdateFactory
                            .newCameraPosition(position), 7000
                    )
                }
            }
            // Map is set up and the style has loaded. Now you can add additional data or make other map adjustments.
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {

    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted && mapboxMap != null) {
            val style = mapboxMap?.style
            style?.let {
                enableCurrentLocation(it)
            }
        } else {

        }
    }
    private fun enableCurrentLocation(loadedMapStyle: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            val locationComponent = mapboxMap?.locationComponent
            //active current location
            val locationActive =
                LocationComponentActivationOptions.builder(this, loadedMapStyle).build()
            locationComponent?.activateLocationComponent(locationActive)

            //active Permission location
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            locationComponent?.isLocationComponentEnabled = true

            //active camera location
            locationComponent?.cameraMode = CameraMode.TRACKING
            locationComponent?.renderMode = RenderMode.NORMAL
            if (BuildConfig.DEBUG && locationComponent?.lastKnownLocation == null) {
                error("Assertion failed")
            }


            //if gps Not Active
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            } else {
                locationlan = locationComponent?.lastKnownLocation?.latitude ?: 35.678494
                locationlong = locationComponent?.lastKnownLocation?.longitude ?: 51.390880
            }

        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager?.requestLocationPermissions(this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        markerViewManager?.onDestroy()
        mapView?.apply {
            onDestroy()
        }
    }


}