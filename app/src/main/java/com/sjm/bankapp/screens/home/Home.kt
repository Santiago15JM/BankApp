package com.sjm.bankapp.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.Session
import com.sjm.bankapp.navigation.NavType
import com.sjm.bankapp.navigation.PayBillKey
import com.sjm.bankapp.navigation.SendMoneyKey
import com.sjm.bankapp.navigation.SettingsKey
import com.sjm.bankapp.navigation.TransactionHistoryKey
import com.sjm.bankapp.ui.Balance
import com.sjm.bankapp.ui.Base
import com.sjm.bankapp.ui.Button
import com.sjm.bankapp.ui.MenuOption
import com.sjm.bankapp.ui.OptionsCard
import com.sjm.bankapp.ui.theme.BankAppTheme

@Composable
fun Home(navigateTo: (NavType) -> Unit, navigateBack: () -> Unit) {
    var animate by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        animate = true
    }
    val greetingAnimation =
        slideInVertically(initialOffsetY = { -it }, animationSpec = tween(1000)) +
                fadeIn(animationSpec = tween(durationMillis = 1000, easing = EaseInCubic))

    val optionCardAnimation =
        slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(600, easing = EaseOutQuint)
        ) +
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        delayMillis = 100,
                        easing = EaseInCubic
                    )
                )

    Base(verticalArrangement = Arrangement.SpaceBetween) {
        AnimatedVisibility(visible = animate, enter = greetingAnimation) {
            Text(
                "Bienvenido\n${Session.userName}",
                fontSize = 40.sp,
                modifier = Modifier.padding(top = 20.dp),
                textAlign = TextAlign.Center,
            )
        }

        Column {
            AnimatedVisibility(
                visible = animate,
                enter = fadeIn(animationSpec = tween(durationMillis = 600, easing = EaseInCubic))
            ) {
                Balance { BankEnd.getBalance().toString() }
            }

            AnimatedVisibility(
                visible = animate, enter = optionCardAnimation
            ) {
                OptionsCard(modifier = Modifier.padding(bottom = 40.dp)) {
                    Text("Menu principal", fontSize = 30.sp)
                    MenuOption("Consultar movimientos", { navigateTo(TransactionHistoryKey) })
                    MenuOption("Enviar dinero", { navigateTo(SendMoneyKey) })
                    MenuOption("Pagar factura", { navigateTo(PayBillKey) })
                    MenuOption("Opciones de la cuenta", { navigateTo(SettingsKey) })
                    Button({
                        navigateBack()
                        Session.stopTimeoutJob()
                    }) {
                        Text("FINALIZAR SESION")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    BankAppTheme {
        Home({}, {})
    }
}