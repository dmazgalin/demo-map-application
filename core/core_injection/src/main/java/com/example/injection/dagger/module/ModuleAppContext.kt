package com.example.injection.dagger.module

import android.app.Application
import android.content.Context
import com.example.injection.annotation.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModuleAppContext (private val application: Application) {

    @Singleton
    @Provides
    @ForApplication
    fun providesContext(): Context {
        return application.applicationContext
    }
}