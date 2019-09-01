package com.example.http.dagger

import com.example.http.interceptor.AuthInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.example.core.api.Config
import com.moczul.ok2curl.CurlInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class InterceptorHttpModule {

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    @Provides
    @Singleton
    fun providesAuthInterceptor(config: Config): AuthInterceptor = AuthInterceptor(config)

    @Provides
    @Singleton
    fun providesCurlInterceptor(): CurlInterceptor = CurlInterceptor()

    @Provides
    @Singleton
    fun providesStethoInterceptorInterceptor(): StethoInterceptor = StethoInterceptor()

}