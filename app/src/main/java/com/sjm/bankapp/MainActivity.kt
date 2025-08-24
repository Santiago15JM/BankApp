package com.sjm.bankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sjm.bankapp.logic.NotificationHelper.createNotificationChannel
import com.sjm.bankapp.logic.RequestNotificationPermission
import com.sjm.bankapp.logic.Session
import com.sjm.bankapp.logic.SoundManager
import com.sjm.bankapp.navigation.NavigationRoot
import com.sjm.bankapp.ui.theme.BankAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Session.instantiateDB(applicationContext)
        SoundManager.init(applicationContext)

        enableEdgeToEdge()
        setContent {
            BankAppTheme {
                BankApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.release()
    }
}

@Composable
fun BankApp() {
    RequestNotificationPermission()
    createNotificationChannel(LocalContext.current)

    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            NavigationRoot()
        }
    }
}
