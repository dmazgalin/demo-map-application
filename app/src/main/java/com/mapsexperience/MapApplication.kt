package com.mapsexperience

import androidx.multidex.MultiDexApplication
import com.example.injection.dagger.module.ModuleAppContext
import com.example.http.dagger.CacheModule
import com.example.http.dagger.CoreHttpModule
import com.example.http.dagger.InterceptorHttpModule
import com.mapsexperience.cache.CacheConfigurationImpl
import com.mapsexperience.dagger.ApplicationComponent
import com.mapsexperience.dagger.ApplicationModule
import com.mapsexperience.dagger.DaggerApplicationComponent

class MapApplication: MultiDexApplication() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
    }

    fun getApplicationComponent() : ApplicationComponent {
        if (!::applicationComponent.isInitialized) {
            applicationComponent = DaggerApplicationComponent.builder()
                .moduleAppContext(ModuleAppContext(this))
                .applicationModule(ApplicationModule())
                .cacheModule(CacheModule())
                .coreHttpModule(CoreHttpModule(CacheConfigurationImpl(this)))
                .interceptorHttpModule(InterceptorHttpModule())
                .build()
        }
        return applicationComponent
    }
}