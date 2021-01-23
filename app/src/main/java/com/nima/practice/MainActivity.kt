package com.nima.practice

import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression
import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.BitmapUtils


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mapView: MapView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loc= LocationManager(this)
        if (loc.canGetLocation()){
            val location = loc.getLocation()
            Log.i("MainActivity", "onCreate: lat : ${location?.latitude} log : ${location?.longitude}")
            Toast.makeText(this,"lat : ${location?.latitude} log : ${location?.longitude}",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"cant get the location",Toast.LENGTH_SHORT).show()
        }

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapView?.
        Mapbox.getInstance(this, getString(R.string.mapBox_token))

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setStyle(
            Style.Builder()
                .fromUri("mapbox://styles/mapbox/streets-v10") // Add images to the map so that the SymbolLayers can reference the images.
                .withImage(
                    ICON_IMAGE_ID, BitmapUtils.getBitmapFromDrawable(
                        resources.getDrawable(R.drawable.ic_location_marker)
                    )!!
                ) // Add GeoJSON data to the GeoJsonSource and then add the GeoJsonSource to the map
                .withSource(
                    GeoJsonSource(
                        SOURCE_ID,
                        FeatureCollection.fromFeatures(initFeatureArray())
                    )
                )
        ) { style -> // Add the base CircleLayer, which will show small circles when the map is zoomed far enough
            // away from the map.
            initCircleSource(style)
            initCircleLayer(style)
            val baseCircleLayer = CircleLayer(
                BASE_CIRCLE_LAYER_ID,
                SOURCE_ID
            ).withProperties(
                PropertyFactory.circleColor(
                    Color.parseColor(
                        BASE_CIRCLE_COLOR
                    )
                ),
                PropertyFactory.circleRadius(
                    Expression.interpolate(
                        Expression.linear(),
                        Expression.zoom(),
                        Expression.stop(
                            ZOOM_LEVEL_FOR_START_OF_BASE_CIRCLE_EXPANSION,
                            BASE_CIRCLE_INITIAL_RADIUS
                        ),
                        Expression.stop(
                            ZOOM_LEVEL_FOR_SWITCH_FROM_CIRCLE_TO_ICON,
                            RADIUS_WHEN_CIRCLES_MATCH_ICON_RADIUS
                        )
                    )
                )
            )
            style.addLayer(baseCircleLayer)

            // Add a "shading" CircleLayer, whose circles' radii will match the radius of the SymbolLayer
            // circular icon
            val shadowTransitionCircleLayer = CircleLayer(
                SHADOW_CIRCLE_LAYER_ID,
                SOURCE_ID
            )
                .withProperties(
                    PropertyFactory.circleColor(
                        Color.parseColor(
                            SHADING_CIRCLE_COLOR
                        )
                    ),
                    PropertyFactory.circleRadius(RADIUS_WHEN_CIRCLES_MATCH_ICON_RADIUS),
                    PropertyFactory.circleOpacity(
                        Expression.interpolate(
                            Expression.linear(),
                            Expression.zoom(),
                            Expression.stop(
                                ZOOM_LEVEL_FOR_START_OF_BASE_CIRCLE_EXPANSION - .5,
                                0
                            ),
                            Expression.stop(
                                ZOOM_LEVEL_FOR_START_OF_BASE_CIRCLE_EXPANSION,
                                FINAL_OPACITY_OF_SHADING_CIRCLE
                            )
                        )
                    )
                )
            style.addLayerBelow(
                shadowTransitionCircleLayer,
                BASE_CIRCLE_LAYER_ID
            )

            // Add the SymbolLayer
            val symbolIconLayer = SymbolLayer(
                ICON_LAYER_ID,
                SOURCE_ID
            )
            symbolIconLayer.withProperties(
                PropertyFactory.iconImage(ICON_IMAGE_ID),
                PropertyFactory.iconSize(1.5f),
                PropertyFactory.iconIgnorePlacement(true),
                PropertyFactory.iconAllowOverlap(true)
            )
            symbolIconLayer.minZoom =
                ZOOM_LEVEL_FOR_SWITCH_FROM_CIRCLE_TO_ICON
            style.addLayer(symbolIconLayer)
            mapboxMap.animateCamera(
                CameraUpdateFactory
                    .newCameraPosition(
                        CameraPosition.Builder()
                            .zoom(12.5)
                            .build()
                    ), 3000
            )
        }
    }

    private fun initCircleSource(loadedMapStyle: Style) {
        loadedMapStyle.addSource(
            GeoJsonSource(
                SOURCE_ID_1, FeatureCollection.fromFeatures(initFeature())
            )
        )
    }

    private fun initCircleLayer(loadedMapStyle: Style) {
        loadedMapStyle.addImage(
            "pre-define-icon-image",
            resources.getDrawable(R.drawable.ic_location_nearest)
        )
        loadedMapStyle.addSource(GeoJsonSource("circle-layer-bounds-corner-id"))
        loadedMapStyle.addLayer(
            SymbolLayer(
                "circle-layer-bounds-corner-id",
                SOURCE_ID_1
            ).withProperties(
                PropertyFactory.iconImage("pre-define-icon-image")
            )
        )
    }

    private fun initFeature(): Array<Feature> {
        return arrayOf(
            Feature.fromGeometry(
                Point.fromLngLat(
                    135.516316,
                    34.681345
                )
            )
        )
    }

    private fun initFeatureArray(): Array<Feature> {
        return arrayOf(
            Feature.fromGeometry(
                Point.fromLngLat(
                    135.509537,
                    34.707929
                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.487953,
//                    34.680369
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.479682,
//                    34.698283
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.499368,
//                    34.708894
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.469701,
//                    34.691089
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.471265,
//                    34.672435
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.485418,
//                    34.704285
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.493762,
//                    34.669337
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.509407,
//                    34.696032
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.492719,
//                    34.68424
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.51045,
//                    34.684133
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.500802,
//                    34.700212
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.519576,
//                    34.698712
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.502888,
//                    34.67888
//                )
//            ),
//            Feature.fromGeometry(
//                Point.fromLngLat(
//                    135.518533,
//                    34.67116
//                )
            )
        )
    }

    public override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    public override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    public override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    public override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    companion object {
        private const val BASE_CIRCLE_INITIAL_RADIUS = 3.4f
        private const val RADIUS_WHEN_CIRCLES_MATCH_ICON_RADIUS = 14f
        private const val ZOOM_LEVEL_FOR_START_OF_BASE_CIRCLE_EXPANSION = 11f
        private const val ZOOM_LEVEL_FOR_SWITCH_FROM_CIRCLE_TO_ICON = 12f
        private const val FINAL_OPACITY_OF_SHADING_CIRCLE = .5f
        private const val BASE_CIRCLE_COLOR = "#3BC802"
        private const val SHADING_CIRCLE_COLOR = "#858585"
        private const val SOURCE_ID = "SOURCE_ID"
        private const val SOURCE_ID_1 = "SOURCE_ID_1"
        private const val ICON_LAYER_ID = "ICON_LAYER_ID"
        private const val BASE_CIRCLE_LAYER_ID = "BASE_CIRCLE_LAYER_ID"
        private const val SHADOW_CIRCLE_LAYER_ID = "SHADOW_CIRCLE_LAYER_ID"
        private const val ICON_IMAGE_ID = "ICON_ID"
    }
}