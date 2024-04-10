package com.spongycode.weatherapp.screens.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spongycode.weatherapp.domain.model.WeatherInfo
import com.spongycode.weatherapp.domain.repository.WeatherRepository
import com.spongycode.weatherapp.screens.ui_events.SnackBarEvent
import com.spongycode.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherInfo = mutableStateOf<WeatherInfo?>(null)
    val weatherInfo: State<WeatherInfo?> = _weatherInfo

    private val _snackBarFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarFlow = _snackBarFlow.asSharedFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    fun fetchWeatherInfo(latitude: Double, longitude: Double) {
        try {
            viewModelScope.launch {
                _isLoading.value = true
                repository.getWeatherInfo(latitude, longitude).collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _snackBarFlow.emit(
                                SnackBarEvent(
                                    show = true,
                                    text = "Some error occurred, please refresh."
                                )
                            )
                            _isLoading.value = false
                        }

                        is Resource.Loading -> {
                            if (result.data != null) {
                                _weatherInfo.value = result.data
                            }
                        }

                        is Resource.Success -> {
                            _weatherInfo.value = result.data!!
                            _isLoading.value = false
                        }
                    }
                }
            }
        } catch (_: Exception) {
            _isLoading.value = false
        }
    }
}