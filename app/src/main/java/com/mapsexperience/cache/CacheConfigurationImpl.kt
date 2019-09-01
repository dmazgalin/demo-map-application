package com.mapsexperience.cache

import android.content.Context
import android.os.StatFs
import com.example.http.cache.CacheConfiguration
import timber.log.Timber
import java.io.File

class CacheConfigurationImpl(private val context: Context) : CacheConfiguration {
    private val apiDir = getCacheDir(apiCacheDirName)

    private val imageDir = getCacheDir(imageCacheDirName)

    private val calculatedImageCacheSize = imageDir.calculateDiskCacheSize(minImageCacheSize, maxImageCacheSize)

    override val apiCacheDirectory: File = apiDir

    override val apiCacheSize: Long = apiCacheSizeInBytes

    override val imageCacheDirectory: File = imageDir

    override val imageCacheSize: Long = calculatedImageCacheSize


    private fun getCacheDir(name: String): File {
        val cacheDir = File(context.cacheDir, name)
        return if (cacheDir.exists() || cacheDir.mkdir()) cacheDir else context.cacheDir
    }

    private fun File.calculateDiskCacheSize(min: Long, max: Long): Long {
        var size = minImageCacheSize

        try {
            val statFs = StatFs(absolutePath)
            val blockCount = statFs.blockCountLong
            val blockSize = statFs.blockSizeLong
            val available = blockCount * blockSize
            // Target 2% of the total space.
            size = (available * targetCacheMultiplier).toLong()
        } catch (error: IllegalArgumentException) {
            Timber.e(error, "calculateDiskCacheSize()")
        }

        // Bound inside min/max size for disk cache.
        return size.coerceIn(min, max)
    }

    private companion object {
        const val apiCacheDirName = "api"
        const val apiCacheSizeInBytes = (5L * 1024 * 1024)

        const val imageCacheDirName = "images"

        const val minImageCacheSize = 5L * 1024 * 1024
        const val maxImageCacheSize = 50L * 1024 * 1024

        const val targetCacheMultiplier = 0.02
    }
}