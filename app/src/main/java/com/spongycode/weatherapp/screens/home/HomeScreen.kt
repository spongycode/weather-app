package com.spongycode.weatherapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spongycode.weatherapp.screens.components.AppBar
import com.spongycode.weatherapp.screens.components.PlaceholderMessageText
import com.spongycode.weatherapp.screens.home.components.CityItem
import com.spongycode.weatherapp.screens.home.components.StateItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val cityMap = viewModel.cityMap
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp

    Scaffold(topBar = {
        AppBar(
            title = "Weather App",
            modifier = Modifier.height((height / 12).dp)
        )
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            cityMap.value.onEachIndexed { _, data ->
                item {
                    StateItem(state = data.key,
                        onClick = { viewModel.toggleExpandState(data.key) },
                        isExpanded = viewModel.expandedState.value == data.key)
                }
                if (viewModel.expandedState.value == data.key) {
                    items(data.value) { city ->
                        CityItem(
                            city = city,
                            onClick = {
                                navController.navigate("detail/${city.lat};${city.lng};${city.city}")
                            }
                        )
                    }
                }
            }
        }
        when (viewModel.homeState.value) {
            HomeState.Error -> PlaceholderMessageText("Oops, some error occurred.")
            HomeState.Loading -> PlaceholderMessageText("Loading cities..")
            HomeState.Success -> {
                Unit
            }
        }
    }
}