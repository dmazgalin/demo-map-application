package com.mapsexperience.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.core.coroutine.CoroutineScopeProvider
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager
import com.mapsexperience.cluster.CarClusterItem
import com.mapsexperience.domain.CarOnMap
import com.mapsexperience.dto.CarDTO
import com.mapsexperience.dto.transformToCarOnMap
import com.mapsexperience.repository.CarsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class MapActivityViewModel(val carsRepository: CarsRepository, val coroutineScopeProvider: CoroutineScopeProvider) : ViewModel() {

    private var job: Job? = null
    private val carsOnMapMutableLiveData = MediatorLiveData<List<CarOnMap>>()
    private var selectedCarVin: String? = null

    fun getCarsOnMapLiveData(): LiveData<List<CarOnMap>> = carsOnMapMutableLiveData

    fun getCarsOnMap() {
        val scope = coroutineScopeProvider.io
        job = scope.launch {
            runCatching {
                computeVisibleCars(coroutineScopeProvider.main.coroutineContext, carsRepository)
            }.onFailure {
                Timber.e(it, "getCarsOnMap() failed with exception")
            }
        }
    }

    private suspend fun computeVisibleCars(scope: CoroutineContext, carsRepository: CarsRepository) {
        val carList = carsRepository.getCars().map { carDTO -> carDTO.transformToCarOnMap(getVisibilityFlagForCar(carDTO)) }
        withContext(scope) {
            carsOnMapMutableLiveData.postValue(carList)
        }
    }

    private fun getVisibilityFlagForCar(carDTO: CarDTO) = when {
        selectedCarVin.isNullOrEmpty() -> true
        carDTO.vin.equals(selectedCarVin) -> true
        else -> false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun handleMarkerClick(carVin: String, clusterManager: ClusterManager<CarClusterItem>) {
        if (selectedCarVin.isNullOrEmpty()) {
            selectedCarVin = carVin
            clusterManager.markerCollection.markers.forEach { marker ->
                marker.isVisible = marker.title.equals(carVin)
                if (marker.isVisible) {
                    marker.showInfoWindow()
                }
            }
            handleClusterMarkers(clusterManager.clusterMarkerCollection.markers, false)
        } else {
            selectedCarVin = null
            clusterManager.markerCollection.markers.forEach { marker ->
                marker.isVisible = true
                if (marker.isInfoWindowShown) {
                    marker.hideInfoWindow()
                }
            }
            handleClusterMarkers(clusterManager.clusterMarkerCollection.markers, true)
        }
    }

    private fun handleClusterMarkers(markers: Collection<Marker>, visible: Boolean) {
        markers.forEach { marker ->
            marker.isVisible = visible
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            }
        }
    }
}