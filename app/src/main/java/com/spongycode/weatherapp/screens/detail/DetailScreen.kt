package com.spongycode.weatherapp.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spongycode.weatherapp.R
import com.spongycode.weatherapp.domain.model.Main
import com.spongycode.weatherapp.domain.model.WeatherInfo
import com.spongycode.weatherapp.domain.model.Wind
import com.spongycode.weatherapp.screens.components.TextBox
import com.spongycode.weatherapp.screens.components.Topbar
import com.spongycode.weatherapp.utils.CelsiusConvert
import com.spongycode.weatherapp.utils.Constants
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreen(
    navController: NavHostController,
    metadata: String = "",
    viewModel: DetailViewModel = hiltViewModel()
) {
    val sep = metadata.split(";")
    val latitude = sep[0].toDouble()
    val longitude = sep[1].toDouble()
    val city = sep[2]

    val snackBarHostState = remember { SnackbarHostState() }
    val isLoading = viewModel.isLoading.value
    val weatherInfo = viewModel.weatherInfo.value

    LaunchedEffect(key1 = true) {
        viewModel.fetchWeatherInfo(latitude, longitude)

        viewModel.snackBarFlow.collectLatest { event ->
            if (event.show) {
                snackBarHostState.showSnackbar(
                    message = event.text,
                    actionLabel = "Okay",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    DetailScreenComponent(
        city = city,
        onBackPressed = { navController.navigateUp() },
        snackBarHostState = snackBarHostState,
        weatherInfo = weatherInfo,
        isLoading = isLoading,
        onRefresh = { viewModel.fetchWeatherInfo(latitude, longitude) }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenComponent(
    city: String = "Sydney",
    onBackPressed: () -> Unit = {},
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    weatherInfo: WeatherInfo? = WeatherInfo(
        latitude = 122.23,
        longitude = -803.124,
        mainData = Main(
            temp = 293.85,
            tempMin = 290.85,
            tempMax = 301.32,
            main = "Rain",
            description = "light intensity shower rain",
            feelsLike = 323.23,
            pressure = 1012.0,
            groundLevel = 32.0,
            humidity = 18.0
        ),
        locationName = "Sydney",
        windData = Wind(speed = 5.96, deg = 328.0)
    ),
    isLoading: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    Scaffold(
        topBar = { Topbar(title = city, onBackPressed = { onBackPressed() }) },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(Constants.MEDIUM_HEIGHT))
            weatherInfo?.let {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(color = Color(0xFFEDEDED))
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextBox(
                            text = CelsiusConvert.toCelsius(weatherInfo.mainData.temp),
                            color = Color(0xFF267BC4),
                            size = 35.sp,
                            weight = FontWeight.W600
                        )
                        TextBox(
                            text = " °C",
                            color = Color(0xFF267BC4),
                            size = 30.sp,
                            weight = FontWeight.W600
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Constants.SMALL_HEIGHT))

                TextBox(
                    text = "Feels like ${CelsiusConvert.toCelsius(weatherInfo.mainData.feelsLike)} °C",
                    weight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(Constants.VERY_SMALL_HEIGHT))
                TextBox(
                    text = "${CelsiusConvert.toCelsius(weatherInfo.mainData.tempMin)} °C - ${
                        CelsiusConvert.toCelsius(
                            weatherInfo.mainData.tempMax
                        )
                    } °C",
                    weight = FontWeight.W500
                )

                Spacer(modifier = Modifier.height(Constants.SMALL_HEIGHT))
                TextBox(
                    text = weatherInfo.mainData.main,
                    size = 24.sp,
                    weight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(Constants.VERY_SMALL_HEIGHT))

                TextBox(
                    text = weatherInfo.mainData.description,
                    weight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(Constants.SMALL_HEIGHT))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        painter = painterResource(id = R.drawable.baseline_waves_24),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                    TextBox(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        text = "${weatherInfo.windData.speed} m/s"
                    )
                    TextBox(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        text = "${weatherInfo.windData.deg}°"
                    )
                }

                Spacer(modifier = Modifier.height(Constants.VERY_SMALL_HEIGHT))
                TextBox(
                    text = "Pressure: ${weatherInfo.mainData.pressure} hPa",
                    weight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(Constants.VERY_SMALL_HEIGHT))
                TextBox(
                    text = "Humidity: ${weatherInfo.mainData.humidity}%",
                    weight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(Constants.SMALL_HEIGHT))
                TextBox(
                    text = weatherInfo.locationName,
                    weight = FontWeight.W600,
                    size = 24.sp,
                )

                Spacer(modifier = Modifier.height(Constants.VERY_SMALL_HEIGHT))
                TextBox(
                    text = "${weatherInfo.latitude} / ${weatherInfo.longitude}",
                    weight = FontWeight.W500
                )
            }
            Spacer(modifier = Modifier.height(Constants.MEDIUM_HEIGHT))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Constants.LARGE_HEIGHT),
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(Constants.LARGE_HEIGHT)
                        .clickable {
                            onRefresh()
                        },
                    painter = painterResource(id = R.drawable.baseline_progress_icon_24),
                    contentDescription = null
                )
            }
        }
    }
}
