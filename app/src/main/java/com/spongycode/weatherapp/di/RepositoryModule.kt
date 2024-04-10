package com.spongycode.weatherapp.di

import android.content.Context
import com.spongycode.weatherapp.data.local.WeatherInfoDatabase
import com.spongycode.weatherapp.data.remote.WeatherApi
import com.spongycode.weatherapp.data.repository.WeatherRepositoryImpl
import com.spongycode.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun bindWeatherRepository(
        context: Context,
        db: WeatherInfoDatabase,
        api: WeatherApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(context, api, db.dao)
    }
}