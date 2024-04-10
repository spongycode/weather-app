package com.spongycode.weatherapp.domain.model

data class WeatherInfo(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val mainData: Main = Main(),
    val windData: Wind = Wind(),
    val locationName: String = "",
)

data class Main(
    val temp: Double = 0.0,
    val feelsLike: Double = 0.0,
    val tempMin: Double = 0.0,
    val tempMax: Double = 0.0,
    val pressure: Double = 0.0,
    val humidity: Double = 0.0,
    val seaLevel: Double = 0.0,
    val groundLevel: Double = 0.0,
    val main: String = "",
    val description: String = ""
)

data class Wind(
    val speed: Double = 0.0,
    val deg: Double = 0.0,
    val gust: Double = 0.0
)