package com.mapsexperience.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.coroutine.CoroutineScopeProvider
import com.mapsexperience.domain.CarInList
import com.mapsexperience.dto.transformToCarInList
import com.mapsexperience.repository.CarsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ListActivityViewModel(val carsRepository: CarsRepository, val coroutineScopeProvider: CoroutineScopeProvider) : ViewModel() {

    private var job: Job? = null
    private val carsInListMutableLiveData = MutableLiveData<List<CarInList>>()

    fun getCarsInListLiveData() : LiveData<List<CarInList>> = carsInListMutableLiveData

    fun getCarsInList() {
        val scope = coroutineScopeProvider.io
        job = scope.launch {
            runCatching {
                val carList = carsRepository.getCars().map { carDTO -> carDTO.transformToCarInList() }
                withContext(coroutineScopeProvider.main.coroutineContext) {
                    carsInListMutableLiveData.postValue(carList)
                }
            }.onFailure {
                Timber.e(it, "getCarsInList() failed with exception")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}