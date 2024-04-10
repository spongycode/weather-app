package com.spongycode.weatherapp.domain.model

data class City(
    val city: String,
    val lat: Double,
    val lng: Double,
    val admin_name: String,
    val country: String
)
