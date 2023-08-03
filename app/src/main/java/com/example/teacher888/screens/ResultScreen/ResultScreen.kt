package com.example.teacher888.screens.ResultScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.teacher888.R


@Composable
fun ResultScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.result), contentDescription = "", modifier = Modifier.fillMaxSize())
    }
}