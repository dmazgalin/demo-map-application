package com.example.core.coroutine

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val main: CoroutineContext

    val io: CoroutineContext
}
