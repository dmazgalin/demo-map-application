package com.mapsexperience.dagger

import com.example.core.coroutine.dagger.CoroutinesModule
import com.example.injection.dagger.module.ModuleAppContext
import com.example.rx.dagger.ModuleCoreRx
import com.example.http.dagger.CacheModule
import com.example.http.dagger.CoreHttpModule
import com.example.http.dagger.InterceptorHttpModule
import com.mapsexperience.ListActivity
import com.mapsexperience.MapActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ModuleAppContext::class,
    ModuleCoreRx::class,
    CacheModule::class,
    CoreHttpModule::class,
    InterceptorHttpModule::class,
    CoroutinesModule::class,
    ApplicationModule::class
])
interface ApplicationComponent {
    fun inject(activity: ListActivity)
    fun inject(activity: MapActivity)
}