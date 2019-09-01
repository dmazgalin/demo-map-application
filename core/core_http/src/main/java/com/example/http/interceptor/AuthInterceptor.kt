package com.example.http.interceptor

import com.example.core.api.Config
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val config: Config) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val rb = original.newBuilder()
            .header("User-Agent", config.userAgent)
            .header("Accept-Language", config.language)
            .method(original.method(), original.body())

        return chain.proceed(rb.build())
    }
}