package com.sjm.bankapp.screens.details

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import com.sjm.bankapp.logic.models.Transaction
import com.sjm.bankapp.navigation.HomeKey
import com.sjm.bankapp.navigation.returnTo
import com.sjm.bankapp.ui.Base
import com.sjm.bankapp.ui.Button
import com.sjm.bankapp.ui.Card
import com.sjm.bankapp.ui.Subtitle
import com.sjm.bankapp.ui.Title
import com.sjm.bankapp.ui.theme.BankAppTheme
import com.sjm.bankapp.ui.theme.Black
import com.sjm.bankapp.ui.theme.accentColor
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import com.sjm.bankend.models.TransactionState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PostPaymentScreen(
    transaction: Transaction, navStack: NavBackStack
) {
    BackHandler {
        navStack.returnTo(HomeKey)
    }

    Base {
        Title(text = "Pago realizado")

        Card(Modifier.padding(20.dp)) {
            Column {
                Row(
                    Modifier
                        .background(accentColor())
                        .padding(16.dp)
                ) {
                    Subtitle("Detalles del pago", color = Black)
                }

                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Fecha: ${transaction.date.format(DateTimeFormatter.ofPattern("d/M/y HH:mm:ss"))}")
                    Text(text = "Cuenta origen: ${transaction.senderAccountId}")
                    Text(text = "Cuenta destino: ${transaction.receiverAccountId}")
                    SelectionContainer {
                        Text("Id: ${transaction.operationId}")
                    }
                }
            }
        }

        Button(
            onClick = { navStack.returnTo(HomeKey) },
            modifier = Modifier.padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(secondaryBtnColor())
        ) {
            Text("REGRESAR")
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES /*showBackground = true, backgroundColor = 0xFF121212*/)
@Composable
fun PreviewPostPayment() {
    BankAppTheme {
        PostPaymentScreen(
            Transaction(
                "00000000-0000000000", 999, 100, 200, LocalDateTime.now(), TransactionState.SUCCESS
            ), NavBackStack()
        )
    }
}