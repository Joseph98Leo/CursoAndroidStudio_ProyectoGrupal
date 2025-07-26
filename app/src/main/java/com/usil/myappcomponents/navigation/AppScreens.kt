package com.usil.myappcomponents.navigation

sealed class AppScreens(val route: String){
    object SplashScreen: AppScreens("splash_screen")
    object QuizScreen: AppScreens("quiz_screen")
    // Puedes añadir más rutas aquí si tuvieras más pantallas
    // object DetailScreen: AppScreens("detail_screen/{id}") {
    //    fun createRoute(id: String) = "detail_screen/$id"
    // }
}