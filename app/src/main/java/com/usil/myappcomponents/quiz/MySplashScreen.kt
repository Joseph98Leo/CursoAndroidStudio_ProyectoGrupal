package com.usil.myappcomponents.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.usil.myappcomponents.navigation.AppScreens
import kotlinx.coroutines.delay

@Composable
fun MySplashScreen(navController: NavHostController){ // Nombre corregido para evitar conflictos
    LaunchedEffect(Unit) {
        delay(3000) // Espera 2 segundos

        navController.navigate(AppScreens.QuizScreen.route){
            popUpTo(AppScreens.SplashScreen.route){
                inclusive = true
            }
        }

        // navController.popBackStack() // Elimina la splash screen del back stack
    }
    SplashContent() // Llama al Composable que muestra el contenido de la splash
}

@Composable
fun SplashContent(){ // Función que dibuja el contenido visual de la splash screen
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        // Asegúrate de tener 'quizz_logo.png' (o .jpg, .xml, etc.) en tu carpeta res/drawable
        Image(
            painter = painterResource(id =
                com.usil.myappcomponents.R.drawable.quizlogo // Reemplaza con el nombre correcto de tu logo
            ),
            contentDescription = "Logo Grupo"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MySplashScreenPreview(){ // Nombre corregido para la función de preview
    SplashContent() // Muestra solo el contenido para la vista previa
}


