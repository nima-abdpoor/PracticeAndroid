package com.nima.practice

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.layers.Layer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    GoogleMap.OnMapClickListener, PermissionsListener {

    var locationlan = 35.678494
    var locationlong = 51.390880

    private val SOURCE_ID = "SOURCE_ID"
    private val ICON_ID = "ICON_ID"
    private val LAYER_ID = "LAYER_ID"
    private lateinit var manager: LocationManager
    private var mapView: MapView? = null
    private val DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID"
    private var mapboxMap: MapboxMap? = null
    private var permissionsManager: PermissionsManager? = null
    private var hoveringMarker: ImageView? = null
    private var droppedMarkerLayer: Layer? = null
    private lateinit var originPosition: Point
    private lateinit var destenitionPosition: Point

    private var marker: Marker? = null
    private var number = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        setContentView(R.layout.activity_main)
        manager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
        mapView = findViewById(R.id.mapViewChose)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        btn_choseLocationF_seeBasig.setOnClickListener {
            if (number > 3) {
                number = 0
            } else number++
            if (number >= 1) {
                onMapReady(mapboxMap!!)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    private fun markPlaces(point: List<LatLng>) {
        for (p in point) {
            marker = mapboxMap?.addMarker(com.mapbox.mapboxsdk.annotations.MarkerOptions().position(p))
//            val icon  = Icon("laskdfj",BitmapFactory.decodeResource(resources,R.drawable.mapbox_marker_icon_default))
//            marker?.icon = icon
//            destenitionPosition = Point.fromLngLat(p.longitude,p.latitude)
        }

//        originPosition = Point.fromLngLat(origi.longitude(),originPosition.longitude())
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        val lat = LatLng()
        this.mapboxMap = mapboxMap
        when (number) {
            0 -> firstOne()
            1 -> secondOne()
            2 -> thirdOne()
            3 -> forthOne()
        }
        val point1 = LatLng(35.678494, 51.390880)
        val point2 = LatLng(36.678494, 52.390880)
        val list = ArrayList<LatLng>()
        list.add(point1)
        list.add(point2)
        markPlaces(list)
//        mapboxMap.setStyle(Style.Builder().fromUri("mapbox://styles/mapbox/streets-v10")) { style ->
//
//        }
    }

    private fun forthOne() {
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
                    BitmapFactory.decodeResource(resources, R.drawable.mapbox_compass_icon)
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

    private fun thirdOne() {
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
                    BitmapFactory.decodeResource(resources, R.drawable.mapbox_marker_icon_default)
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

    private fun secondOne() {
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

    private fun firstOne() {
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
                    BitmapFactory.decodeResource(resources, R.drawable.mapbox_marker_icon_default)
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

    override fun onMapClick(point: LatLng): Boolean {
        return false
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }


}