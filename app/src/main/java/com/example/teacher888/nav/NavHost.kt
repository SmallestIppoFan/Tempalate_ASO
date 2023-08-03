package com.example.quizaso.navigation

import FakeScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parimatchaso.screens.MainScreen.MainScreen
import com.example.quizaso.screens.SplashScreen.SplashScreen
import com.example.teacher888.screnssss.res.ResultScreen
import com.example.quizaso.screens.StartPage.StartScreen
import com.example.teacher888.screnssss.WebViewScreen.WebViewScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.name){
        composable(Screens.MainScreen.name){
            MainScreen(navController=navController)
        }
        composable(Screens.StartScreen.name){
            StartScreen(navController)
        }
        composable(Screens.FakeScreen.name){
            FakeScreen(navController)
        }
        composable(Screens.ResultPage.name){
            ResultScreen()
        }
        composable(Screens.SplashScreen.name){
            SplashScreen(navController = navController )
        }
        composable(Screens.WebViewScreen.name){
            WebViewScreen()
        }
    }
}