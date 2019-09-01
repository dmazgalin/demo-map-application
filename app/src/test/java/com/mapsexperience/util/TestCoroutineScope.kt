package com.mapsexperience.util


import com.example.core.coroutine.CoroutineContextProvider
import com.example.core.coroutine.CoroutineScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestCoroutineScopeProvider  constructor(scope: TestCoroutineScope) : CoroutineScopeProvider {

    private val coroutineContextProvider = TestCoroutineContextProvider(scope)

    override val ioSupervisor: CoroutineScope
        get() = object : CoroutineScope {
            override val coroutineContext: CoroutineContext
                get() = coroutineContextProvider.io + SupervisorJob()
        }
    override val io: CoroutineScope
        get() = object : CoroutineScope {
            override val coroutineContext: CoroutineContext
                get() = coroutineContextProvider.io + Job()
        }
    override val main: CoroutineScope
        get() = object : CoroutineScope {
            override val coroutineContext: CoroutineContext
                get() = coroutineContextProvider.main + Job()
        }
    override val mainSupervisor: CoroutineScope
        get() = object : CoroutineScope {
            override val coroutineContext: CoroutineContext
                get() = coroutineContextProvider.main + SupervisorJob()
        }
}

@ExperimentalCoroutinesApi
class TestCoroutineContextProvider  constructor(val scope: TestCoroutineScope) : CoroutineContextProvider {
    override val main: CoroutineContext
        get() = scope.coroutineContext
    override val io: CoroutineContext
        get() = scope.coroutineContext
}
