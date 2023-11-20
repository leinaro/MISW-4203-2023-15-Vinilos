package com.misw.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    //val systemUiController = rememberSystemUiController()

    //systemUiController.isSystemBarsVisible = false
    //systemUiController.isNavigationBarVisible = false
    //systemUiController.isStatusBarVisible = false

    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        scope.launch {
            //launch { loginViewModel.processIntents(LoginIntent.StoredToken(context)) }
            //loginViewModel.state.collect { state ->
              //  if (state.StoredToken != null) {
                    delay(5000L)
                    navigateToHome()
             //   }
           //}
        }
    }

    Box(
        modifier = Modifier
            .testTag("SplashScreen")
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(RawRes(R.raw.loading))
        LottieAnimation(
            composition = composition,
            contentScale = ContentScale.FillBounds,
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Vinilos",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    SplashScreen()
}
