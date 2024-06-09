package com.abmodel.proyectomoviles.ui.theme.navigation

sealed class ScreenRoute(val route: String) {
    object Home : ScreenRoute("home")
    object AddFamilia : ScreenRoute("add_familia")
    object FamilyDetails : ScreenRoute("family_details/{familyId}") {
        fun createRoute(familyId: Int) = "family_details/$familyId"
    }
    object AddPersona : ScreenRoute("add_persona/{familyId}") {
        fun createRoute(familyId: Int) = "add_persona/$familyId"
    }
    object PersonaDetails : ScreenRoute("persona_details/{personaId}") {
        fun createRoute(personaId: Int) = "persona_details/$personaId"
    }
}

