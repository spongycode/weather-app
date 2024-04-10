package com.spongycode.weatherapp.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spongycode.weatherapp.domain.model.City
import com.spongycode.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _cityMap = mutableStateOf<Map<String, List<City>>>(mutableMapOf())
    val cityMap: State<Map<String, List<City>>> = _cityMap

    private val _expandedState = mutableStateOf("")
    val expandedState: State<String> = _expandedState

    private val _homeState = mutableStateOf<HomeState>(HomeState.Loading)
    val homeState: State<HomeState> = _homeState

    init {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            try {
                _homeState.value = HomeState.Loading
                val cities = weatherRepository.loadCitiesFromJson()
                val cityMap = mutableMapOf<String, MutableList<City>>()
                for (cityData in cities) {
                    val state = cityData.admin_name
                    if (!cityMap.containsKey(state)) {
                        cityMap[state] = mutableListOf()
                    }

                    cityMap[state]?.add(
                        City(
                            city = cityData.city,
                            lat = cityData.lat,
                            lng = cityData.lng,
                            admin_name = state,
                            country = cityData.country
                        )
                    )
                }

                _cityMap.value = cityMap

                _homeState.value = HomeState.Success
            } catch (e: Exception) {
                _homeState.value = HomeState.Error
            }
        }
    }

    fun toggleExpandState(state: String) {
        if (expandedState.value == state) {
            _expandedState.value = ""
        } else {
            _expandedState.value = state
        }
    }

}