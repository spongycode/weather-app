package com.spongycode.weatherapp.domain.repository

import com.spongycode.weatherapp.domain.model.City
import com.spongycode.weatherapp.domain.model.WeatherInfo
import com.spongycode.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherInfo(latitude: Double, longitude: Double): Flow<Resource<WeatherInfo>>

    fun loadCitiesFromJson(): List<City>
}