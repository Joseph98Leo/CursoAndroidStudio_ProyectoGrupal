package com.usil.myappcomponents.navigation

import com.usil.myappcomponents.quiz.MySplashScreen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.usil.myappcomponents.quiz.QuizQuestionActivity
import com.usil.myappcomponents.quiz.QuizScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route // La aplicación inicia en la Splash Screen
    ){
        // Ruta para la pantalla de carga (Splash Screen)

        composable(AppScreens.SplashScreen.route){
            MySplashScreen(navController = navController)
        }
        composable(AppScreens.SplashScreen.route){
            QuizScreen()
        }




    }
}