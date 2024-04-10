package com.spongycode.weatherapp.data.repository

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spongycode.weatherapp.data.local.WeatherInfoDao
import com.spongycode.weatherapp.data.remote.WeatherApi
import com.spongycode.weatherapp.data.remote.dto.Coord
import com.spongycode.weatherapp.domain.model.City
import com.spongycode.weatherapp.domain.model.WeatherInfo
import com.spongycode.weatherapp.domain.repository.WeatherRepository
import com.spongycode.weatherapp.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val api: WeatherApi,
    private val dao: WeatherInfoDao
) : WeatherRepository {
    override fun getWeatherInfo(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<WeatherInfo>> = flow {
        emit(Resource.Loading())
        try {
            val weatherInfo =
                dao.getWeatherInfo(Coord(lat = latitude, lon = longitude))?.toWeatherInfo()
            emit(Resource.Loading(data = weatherInfo))
            try {
                val remoteWeatherInfo =
                    api.getWeatherByCoordinate(latitude = latitude, longitude = longitude)
                dao.deleteWeatherInfo(remoteWeatherInfo.coord)
                dao.insertWeatherInfo(remoteWeatherInfo.toWeatherInfoEntity())
                val newWeatherInfo =
                    dao.getWeatherInfo(Coord(lat = latitude, lon = longitude))?.toWeatherInfo()
                emit(Resource.Success(newWeatherInfo))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = "Something went wrong.",
                        data = weatherInfo
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "Internet connection error.",
                        data = weatherInfo
                    )
                )
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Something went wrong.",
                        data = weatherInfo
                    )
                )
            }
        } catch (_: Exception) {
        }
    }

    override fun loadCitiesFromJson(): List<City> {
        val gson = Gson()
        val listType = object : TypeToken<List<City>>() {}.type
        return gson.fromJson(context.assets.readAssetsFile("au_cities.json"), listType)
    }

    private fun AssetManager.readAssetsFile(fileName: String): String =
        open(fileName).bufferedReader().use { it.readText() }
}