package com.spongycode.weatherapp.utils

object CelsiusConvert {
    fun toCelsius(temp: Double): String {
        return "%.2f".format(temp - 273.15)
    }
}