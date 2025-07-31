package com.sjm.bankapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.sjm.bankapp.config.ConnectivityObserver
import com.sjm.bankapp.config.NetworkObserver
import com.sjm.bankapp.logic.LocalStorage
import com.sjm.bankapp.logic.NotificationHelper.createNotificationChannel
import com.sjm.bankapp.logic.RequestNotificationPermission
import com.sjm.bankapp.screens.NavGraphs
import com.sjm.bankapp.ui.GenericDialog
import com.sjm.bankapp.ui.theme.BankAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalStorage.instantiateDB(applicationContext)
        setContent {
            BankApp()
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun BankApp() {
    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.Center,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = {
                fadeIn(animationSpec = tween(400))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
        )
    )

    val connectivityObserver: ConnectivityObserver = NetworkObserver(LocalContext.current)
    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Available
    )

    RequestNotificationPermission()
    createNotificationChannel(LocalContext.current)

    BankAppTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                DestinationsNavHost(
                    navGraph = NavGraphs.root, engine = navHostEngine
                )
                val c = LocalContext.current as Activity
                if (status != ConnectivityObserver.Status.Available) {
                    GenericDialog(
                        text = "No tienes conexión a internet",
                        subtext = "Restablece tu conexión o intenta mas tarde",
                        buttonText = "SALIR",
                        onButtonClick = { c.finish() },
                        onDismissRequest = {},
                    )
                }
            }
        }
    }
}
