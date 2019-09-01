package com.example.http.cache

import java.io.File

interface CacheConfiguration {
    /**
     * Api cache location.
     */
    val apiCacheDirectory: File

    /**
     * Api cache size in bytes.
     */
    val apiCacheSize: Long

    /**
     * Image cache location.
     */
    val imageCacheDirectory: File

    /**
     * Image cache size in bytes.
     */
    val imageCacheSize: Long
}