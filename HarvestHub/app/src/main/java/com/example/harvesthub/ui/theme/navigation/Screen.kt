package com.example.harvesthub.ui.theme.navigation

sealed class Screen(val route:String){
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailBarang : Screen("home/{barangId}") {
        fun createRoute(barangId: Long) = "home/$barangId"
    }
}
