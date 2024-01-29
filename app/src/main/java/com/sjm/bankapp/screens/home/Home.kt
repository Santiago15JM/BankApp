package com.sjm.bankapp.screens.home

import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.LocalStorage
import com.sjm.bankapp.screens.Balance
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.MenuOption
import com.sjm.bankapp.screens.OptionsCard
import com.sjm.bankapp.screens.destinations.HistoryDestination
import com.sjm.bankapp.screens.destinations.PayBillDestination
import com.sjm.bankapp.screens.destinations.SendCashDestination
import com.sjm.bankapp.screens.destinations.SettingsDestination

@Destination
@Composable
fun Home(navigator: DestinationsNavigator) {
    Base {
        Text(
            "Bienvenido\n${LocalStorage.userName}",
            fontSize = 40.sp,
            modifier = Modifier
                .weight(2f)
                .offset(y = 20.dp),
            textAlign = TextAlign.Center,
        )

        Balance()

        OptionsCard(Modifier.weight(3f)) {
            Text("Menu principal", fontSize = 30.sp)
            MenuOption("Consultar movimientos", { navigator.navigate(HistoryDestination) })
            MenuOption("Enviar dinero", { navigator.navigate(SendCashDestination) })
            MenuOption("Pagar factura", { navigator.navigate(PayBillDestination) })
            MenuOption("Opciones de la cuenta", { navigator.navigate(SettingsDestination) })
            Button({ navigator.navigateUp() }) {
                Text("FINALIZAR SESION")
            }
        }
    }
}
