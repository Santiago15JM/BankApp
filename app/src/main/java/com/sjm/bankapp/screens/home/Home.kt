package com.sjm.bankapp.screens.home

import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.LocalStorage
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

@Composable
fun Home(navigateTo: (NavType) -> Unit, navigateBack: () -> Unit) {
    Base {
        Text(
            "Bienvenido\n${LocalStorage.userName}",
            fontSize = 40.sp,
            modifier = Modifier
                .weight(2f)
                .offset(y = 20.dp),
            textAlign = TextAlign.Center,
        )

        Balance { BankEnd.getBalance().toString() }

        OptionsCard(Modifier.weight(3f)) {
            Text("Menu principal", fontSize = 30.sp)
            MenuOption("Consultar movimientos", { navigateTo(TransactionHistoryKey) })
            MenuOption("Enviar dinero", { navigateTo(SendMoneyKey) })
            MenuOption("Pagar factura", { navigateTo(PayBillKey) })
            MenuOption("Opciones de la cuenta", { navigateTo(SettingsKey) })
            Button(navigateBack) {
                Text("FINALIZAR SESION")
            }
        }
    }
}
