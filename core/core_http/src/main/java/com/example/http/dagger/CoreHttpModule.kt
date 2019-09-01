package com.example.http.dagger

import com.example.http.cache.CacheConfiguration
import com.example.http.interceptor.AuthInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.moczul.ok2curl.CurlInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.example.injection.annotation.ForApi
import com.example.injection.annotation.ForImages
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class CoreHttpModule(private val cacheConfiguration: CacheConfiguration) {

    @Singleton
    @ForImages
    @Provides
    fun provideHttpClientForImages(x509TrustManager: X509TrustManager, @ForImages cache: Cache): OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(x509TrustManager)
        val sc = SSLContext.getInstance("TLS")
        sc.init(null, trustAllCerts, java.security.SecureRandom())
        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().header("User-Agent", "Mozilla/5.0").build()
                chain.proceed(request)
            }
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .hostnameVerifier { s, sslSession -> true }
            .sslSocketFactory(sc.getSocketFactory(), x509TrustManager)
            .cache(cache)
        return httpClient.build()
    }

    @Provides
    @ForApi
    @Singleton
    fun providesOkHttpForApi(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        curlInterceptor: CurlInterceptor,
        stethoInterceptor: StethoInterceptor,
        @ForApi cache: Cache
    ) = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(curlInterceptor)
        .addNetworkInterceptor(stethoInterceptor)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .cache(cache)
        .build()

    @Provides
    fun providesCacheConfiguration(): CacheConfiguration = cacheConfiguration

    @Provides
    fun providesTrustManager() = object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }
}