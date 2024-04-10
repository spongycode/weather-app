package com.spongycode.weatherapp.data.remote

import com.spongycode.weatherapp.data.remote.dto.WeatherInfoDto
import com.spongycode.weatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeatherByCoordinate(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appId") apiKey: String = Constants.API_KEY
    ): WeatherInfoDto

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}