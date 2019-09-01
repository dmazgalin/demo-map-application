package com.mapsexperience.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.coroutine.CoroutineScopeProvider
import com.mapsexperience.repository.CarsRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(val carsRepository: CarsRepository, val coroutineScopeProvider: CoroutineScopeProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MapActivityViewModel::class.java) -> MapActivityViewModel(carsRepository, coroutineScopeProvider) as T
        modelClass.isAssignableFrom(ListActivityViewModel::class.java) -> ListActivityViewModel(carsRepository, coroutineScopeProvider) as T
        else -> throw IllegalArgumentException("View model factory has no support for this type ${modelClass.canonicalName}")
    }

}