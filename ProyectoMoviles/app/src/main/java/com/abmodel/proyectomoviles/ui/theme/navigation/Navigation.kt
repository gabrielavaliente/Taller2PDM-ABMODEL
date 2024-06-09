package com.abmodel.proyectomoviles.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abmodel.proyectomoviles.MainViewModel
import com.abmodel.proyectomoviles.ui.theme.screens.AddFamilia
import com.abmodel.proyectomoviles.ui.theme.screens.AddPersona
import com.abmodel.proyectomoviles.ui.theme.screens.FamilyDetailsScreen
import com.abmodel.proyectomoviles.ui.theme.screens.HomeScreen
import com.abmodel.proyectomoviles.ui.theme.screens.PersonaDetailsScreen

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


