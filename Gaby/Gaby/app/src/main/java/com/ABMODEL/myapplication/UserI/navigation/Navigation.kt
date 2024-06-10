package com.ABMODEL.myapplication.UserI.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ABMODEL.myapplication.MainViewModel
import com.ABMODEL.myapplication.UserI.screens.AddFamilia
import com.ABMODEL.myapplication.UserI.screens.AddPersona
import com.ABMODEL.myapplication.UserI.screens.FamilyDetailsScreen
import com.ABMODEL.myapplication.UserI.screens.HomeScreen
import com.ABMODEL.myapplication.UserI.screens.PersonaDetailsScreen

@Composable
fun Navigation(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.route
    ) {
        composable(route = ScreenRoute.Home.route) {
            HomeScreen(viewModel, navController)
        }
        composable(route = ScreenRoute.AddFamilia.route) {
            AddFamilia(viewModel, navController)
        }
        composable(
            route = ScreenRoute.FamilyDetails.route,
            arguments = listOf(navArgument("familyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val familyId = backStackEntry.arguments?.getInt("familyId") ?: return@composable
            FamilyDetailsScreen(viewModel, familyId, navController)
        }
        composable(
            route = ScreenRoute.AddPersona.route,
            arguments = listOf(navArgument("familyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val familyId = backStackEntry.arguments?.getInt("familyId") ?: return@composable
            AddPersona(viewModel, familyId, navController)
        }
        composable(
            route = ScreenRoute.PersonaDetails.route,
            arguments = listOf(navArgument("personaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val personaId = backStackEntry.arguments?.getInt("personaId") ?: return@composable
            PersonaDetailsScreen(viewModel, personaId, navController)
        }
    }
}
