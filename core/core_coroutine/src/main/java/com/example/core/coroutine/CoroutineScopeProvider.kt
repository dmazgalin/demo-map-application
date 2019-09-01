package com.example.core.coroutine

import kotlinx.coroutines.CoroutineScope

interface CoroutineScopeProvider {
    val ioSupervisor: CoroutineScope

    val io: CoroutineScope

    val main: CoroutineScope

    val mainSupervisor: CoroutineScope
}
