package com.example.rx.dagger

import com.example.rx.scheduler.SchedulerConfiguration
import com.example.rx.scheduler.SchedulerConfigurationImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModuleCoreRx {

    @Singleton
    @Provides
    fun providesSchedulerConfiguration() : SchedulerConfiguration = SchedulerConfigurationImpl()
}