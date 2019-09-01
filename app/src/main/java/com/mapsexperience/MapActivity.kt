package com.mapsexperience

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.mapsexperience.cluster.CarClusterItem
import com.mapsexperience.cluster.CarClusterRender
import com.mapsexperience.dagger.Injector
import com.mapsexperience.viewmodel.MapActivityViewModel
import com.mapsexperience.viewmodel.ViewModelFactory
import javax.inject.Inject

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: MapActivityViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<CarClusterItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MapActivityViewModel::class.java)
        viewModel.getCarsOnMapLiveData().observe(this, Observer { carList ->
            mMap.clear()
            val boundsBuilder = LatLngBounds.builder()
            carList.forEach {
                val carMarker = LatLng(it.lat, it.lon)
                val markerOptions = MarkerOptions()
                    .position(carMarker)
                    .title(it.id)
                    .visible(it.visible)
                val clusterItem = CarClusterItem(markerOptions.position, markerOptions.title)
                clusterManager.addItem(clusterItem)
                boundsBuilder.include(carMarker)
            }
            clusterManager.cluster()
            val bounds = boundsBuilder.build()
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 1))
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        clusterManager = ClusterManager(this, mMap)
        val clusterRenderer = CarClusterRender(this, googleMap, clusterManager)
        clusterManager.setRenderer(clusterRenderer)

        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(this)

        viewModel.getCarsOnMap()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.position))

        marker.title?.let {
            viewModel.handleMarkerClick(it, clusterManager)
        }

        return true
    }
}
