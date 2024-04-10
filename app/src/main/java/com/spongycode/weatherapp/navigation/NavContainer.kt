package com.spongycode.weatherapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spongycode.weatherapp.screens.detail.DetailScreen
import com.spongycode.weatherapp.screens.home.HomeScreen

@Composable
fun NavContainer(startDestination: String) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = "home") {
            HomeScreen(navController = navController)
        }
        composable(route = "detail/{metadata}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(100)
                )
            },
            popEnterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(100)
                )
            }) {
            val metadata = it.arguments?.getString("metadata")
            DetailScreen(navController = navController, metadata = metadata.toString())
        }
    }
}