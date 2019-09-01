package com.example.http.dagger


import com.example.http.cache.CacheConfiguration
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import com.example.injection.annotation.ForApi
import com.example.injection.annotation.ForImages

@Module
class CacheModule {

    @Provides
    @ForImages
    fun providesImageCache(cacheConfiguration: CacheConfiguration) = Cache(
        cacheConfiguration.imageCacheDirectory,
        cacheConfiguration.imageCacheSize
    )

    @Provides
    @ForApi
    fun providesApiCache(cacheConfiguration: CacheConfiguration) = Cache(
        cacheConfiguration.apiCacheDirectory,
        cacheConfiguration.apiCacheSize
    )
}