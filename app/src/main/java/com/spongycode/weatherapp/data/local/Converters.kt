package com.spongycode.weatherapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.spongycode.weatherapp.data.remote.dto.Coord
import com.spongycode.weatherapp.data.remote.dto.Main
import com.spongycode.weatherapp.data.remote.dto.Sys
import com.spongycode.weatherapp.data.remote.dto.Weather
import com.spongycode.weatherapp.data.remote.dto.Wind
import com.spongycode.weatherapp.utils.JsonParser

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromCoordJson(json: String): Coord {
        return jsonParser.fromJson<Coord>(
            json,
            object : TypeToken<Coord>() {}.type
        ) ?: Coord(lon = 0.0, lat = 0.0)
    }

    @TypeConverter
    fun toCoordJson(coord: Coord): String {
        return jsonParser.toJson(
            coord,
            object : TypeToken<Coord>() {}.type
        ) ?: ""
    }

    @TypeConverter
    fun fromWeatherJson(json: String): List<Weather> {
        return jsonParser.fromJson<List<Weather>>(
            json,
            object : TypeToken<List<Weather>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toWeatherJson(weather: List<Weather>): String {
        return jsonParser.toJson(
            weather,
            object : TypeToken<List<Weather>>() {}.type
        ) ?: ""
    }

    @TypeConverter
    fun fromMainJson(json: String): Main {
        return jsonParser.fromJson<Main>(
            json,
            object : TypeToken<Main>() {}.type
        ) ?: Main(
            temp = 0.0, feels_like = 0.0, temp_min = 0.0, temp_max = 0.0,
            pressure = 0.0, humidity = 0.0, sea_level = 0.0, grnd_level = 0.0
        )
    }

    @TypeConverter
    fun toMainJson(main: Main): String {
        return jsonParser.toJson(
            main,
            object : TypeToken<Main>() {}.type
        ) ?: ""
    }

    @TypeConverter
    fun fromWindJson(json: String): Wind {
        return jsonParser.fromJson<Wind>(
            json,
            object : TypeToken<Wind>() {}.type
        ) ?: Wind(speed = 0.0, deg = 0.0, gust = 0.0)
    }

    @TypeConverter
    fun toWindJson(wind: Wind): String {
        return jsonParser.toJson(
            wind,
            object : TypeToken<Wind>() {}.type
        ) ?: ""
    }

    @TypeConverter
    fun fromSysJson(json: String): Sys {
        return jsonParser.fromJson<Sys>(
            json,
            object : TypeToken<Sys>() {}.type
        ) ?: Sys(country = "", sunrise = 0, sunset = 0)
    }

    @TypeConverter
    fun toSysJson(sys: Sys): String {
        return jsonParser.toJson(
            sys,
            object : TypeToken<Sys>() {}.type
        ) ?: ""
    }
}