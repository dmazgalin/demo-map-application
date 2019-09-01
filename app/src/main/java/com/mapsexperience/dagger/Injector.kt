package com.mapsexperience.dagger

import com.mapsexperience.ListActivity
import com.mapsexperience.MapActivity
import com.mapsexperience.MapApplication

object Injector {
    fun inject(activity: MapActivity) {
        (activity.applicationContext as MapApplication).getApplicationComponent().inject(activity)
    }

    fun inject(activity: ListActivity) {
        (activity.applicationContext as MapApplication).getApplicationComponent().inject(activity)
    }
}