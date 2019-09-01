package com.mapsexperience.dagger

import android.content.Context
import androidx.room.Room
import com.example.injection.annotation.ForApplication
import com.google.gson.Gson
import com.example.core.api.Config
import com.mapsexperience.api.RemoteApi
import com.mapsexperience.repository.CarsRepository
import com.mapsexperience.repository.CarsRepositoryImpl
import com.mapsexperience.room.MapApplicationRoomDb
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.injection.annotation.ForApi
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun providesCarRepository(remoteApi: RemoteApi, db: MapApplicationRoomDb): CarsRepository {
        return CarsRepositoryImpl(remoteApi, db)
    }

    @Provides
    @Singleton
    @ForApi
    fun providesRetrofit(configuration: Config, gson: Gson, @ForApi okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(configuration.endpoint)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun providesRemoteApi(@ForApi retrofit: Retrofit): RemoteApi {
        val remoteApi = retrofit.create(RemoteApi::class.java)
        return remoteApi
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesConfig(): Config = object : Config {
        override val endpoint: String
            get() = "http://data.m-tribes.com"
        override val mediaEndpoint: String
            get() = "http://data.m-tribes.com"
        override val userAgent: String
            get() = "Agent"
        override val language: String
            get() = "en"

    }

    @Provides
    @Singleton
    fun providesDataBase(@ForApplication context: Context): MapApplicationRoomDb {
        return Room.databaseBuilder(
            context,
            MapApplicationRoomDb::class.java, "map-application-db"
        ).build()
    }
}