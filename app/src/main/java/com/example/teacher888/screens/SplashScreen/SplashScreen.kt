package com.example.quizaso.screens.SplashScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizaso.navigation.Screens
import com.example.teacher888.R


@SuppressLint("ProduceStateDoesNotAssignValue")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.9f))
            IconButton(onClick = {
                    navController.navigate(Screens.FakeScreen.name) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
            }) {
                Surface(
                    modifier = Modifier
                        .width(180.dp)
                        .height(60.dp), color = Color.Transparent
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.button),
                        contentDescription = "", modifier = Modifier.fillMaxSize()
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "PLAY",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = Color.White.copy(alpha = 0.8f),

                            )

                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }

}


@Composable
fun DotsFlashing() {
    val minAlpha = 0.1f
    val delayUnit=300
    @Composable
    fun Dot(
        alpha: Float
    ) = Spacer(
        Modifier
            .size(20.dp)
            .alpha(alpha)
            .background(
                color = MaterialTheme.colors.primary,
                shape = CircleShape
            )
    )
    val infiniteTransition = rememberInfiniteTransition()


    @Composable
    fun animateAlphaWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = minAlpha,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                minAlpha at delay with LinearEasing
                1f at delay + delayUnit with LinearEasing
                minAlpha at delay + delayUnit * 2
            }
        )
    )
    val alpha1 by animateAlphaWithDelay(0)
    val alpha2 by animateAlphaWithDelay(delayUnit)
    val alpha3 by animateAlphaWithDelay(delayUnit * 2)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val spaceSize = 2.dp
        Dot(alpha1)
        Spacer(Modifier.width(spaceSize))
        Dot(alpha2)
        Spacer(Modifier.width(spaceSize))
        Dot(alpha3)
    }
}
