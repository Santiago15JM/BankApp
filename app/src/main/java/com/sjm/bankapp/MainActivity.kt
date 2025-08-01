package com.sjm.bankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.sjm.bankapp.config.ConnectivityObserver
import com.sjm.bankapp.config.NetworkObserver
import com.sjm.bankapp.logic.LocalStorage
import com.sjm.bankapp.logic.NotificationHelper.createNotificationChannel
import com.sjm.bankapp.logic.RequestNotificationPermission
import com.sjm.bankapp.navigation.NavigationRoot
import com.sjm.bankapp.ui.NoNetworkDialog
import com.sjm.bankapp.ui.theme.BankAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalStorage.instantiateDB(applicationContext)

        enableEdgeToEdge()
        setContent {
            BankAppTheme {
                BankApp()
            }
        }
    }
}

@Composable
fun BankApp() {
    val connectivityObserver: ConnectivityObserver = NetworkObserver(LocalContext.current)
    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Available
    )

    RequestNotificationPermission()
    createNotificationChannel(LocalContext.current)

    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            NavigationRoot()

            if (status != ConnectivityObserver.Status.Available) {
                val a = LocalActivity.current
                NoNetworkDialog(onClick = { a?.finish() })
            }
        }
    }
}
