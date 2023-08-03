package com.example.onexasoaso.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.parimatchaso.theme.Purple200
import com.example.parimatchaso.theme.Purple500
import com.example.parimatchaso.theme.Purple700
import com.example.parimatchaso.theme.StatusBarColor
import com.example.parimatchaso.theme.Teal200
import com.example.parimatchaso.theme.ZAZA
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TEACHER888Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()

    val colors = if (darkTheme) {
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
    if(darkTheme){
        systemUiController.setSystemBarsColor(
            color = StatusBarColor
        )
    }else{
        systemUiController.setSystemBarsColor(
            color = StatusBarColor
        )
    }
}