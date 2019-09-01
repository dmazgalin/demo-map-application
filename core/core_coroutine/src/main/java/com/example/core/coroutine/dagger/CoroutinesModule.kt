package com.example.core.coroutine.dagger

import com.example.core.coroutine.CoroutineContextProvider
import com.example.core.coroutine.CoroutineScopeProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class CoroutinesModule {

    @Provides
    @Singleton
    internal fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return object : CoroutineContextProvider {
            override val main: CoroutineContext = Dispatchers.Main
            override val io: CoroutineContext = Dispatchers.IO
        }
    }

    @Provides
    @Singleton
    internal fun provideCoroutineScopeProvider(
        contextProvider: CoroutineContextProvider
    ): CoroutineScopeProvider = object : CoroutineScopeProvider {
        override val main: CoroutineScope
            get() = object : CoroutineScope {
                override val coroutineContext: CoroutineContext
                    get() = contextProvider.main + Job()
            }

        override val mainSupervisor: CoroutineScope
            get() = object : CoroutineScope {
                override val coroutineContext: CoroutineContext
                    get() = contextProvider.main + SupervisorJob()
            }

        override val ioSupervisor: CoroutineScope
            get() = object : CoroutineScope {
                override val coroutineContext: CoroutineContext
                    get() = contextProvider.io + SupervisorJob()
            }

        override val io: CoroutineScope
            get() = object : CoroutineScope {
                override val coroutineContext: CoroutineContext
                    get() = contextProvider.io + Job()
            }
    }
}