package com.spongycode.weatherapp.di

import android.app.Application
import com.spongycode.weatherapp.data.local.WeatherInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.room.Room
import com.google.gson.Gson
import com.spongycode.weatherapp.data.local.Converters
import com.spongycode.weatherapp.utils.GsonParser

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideWeatherInfoDatabase(app: Application): WeatherInfoDatabase {
        return Room.databaseBuilder(
            app, WeatherInfoDatabase::class.java, "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }
}