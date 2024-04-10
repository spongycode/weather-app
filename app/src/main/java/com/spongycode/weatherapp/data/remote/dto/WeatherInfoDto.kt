package com.spongycode.weatherapp.data.remote.dto

import com.spongycode.weatherapp.data.local.entity.WeatherInfoEntity

data class WeatherInfoDto(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
) {
    fun toWeatherInfoEntity(): WeatherInfoEntity {
        return WeatherInfoEntity(
            coordinate = coord,
            weather = weather,
            base = base,
            main = main,
            visibility = visibility,
            wind = wind,
            sys = sys,
            id = id,
            locationName = name,
        )
    }
}

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val humidity: Double,
    val sea_level: Double,
    val grnd_level: Double
)

data class Wind(
    val speed: Double,
    val deg: Double,
    val gust: Double
)

data class Clouds(
    val all: Int
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
