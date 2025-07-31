package com.sjm.bankapp.screens.details

import android.content.res.Configuration
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.sjm.bankapp.logic.models.Entry
import com.sjm.bankapp.logic.models.Transaction
import com.sjm.bankapp.logic.models.TransactionType
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
@Destination
fun TransactionDetails(
    transaction: Transaction, entry: Entry, nav: DestinationsNavigator
) {
    Base {
        Title("Transacción")

        Card(Modifier.padding(20.dp)) {
            Column {
                Row(
                    Modifier
                        .background(accentColor())
                        .padding(16.dp)
                ) {
                    Subtitle("Detalles de la transacción", color = Black)
                }

                Column(
                    Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text("Fecha: ${transaction.date.format(DateTimeFormatter.ofPattern("d/M/y HH:mm:ss"))}")
                    Text("Tipo de movimiento: ${entry.type.text}")
                    Text("Cuenta origen: ${transaction.senderAccountId}")
                    Text("Cuenta destino: ${transaction.receiverAccountId}")
                    Text("Saldo resultante: ${entry.resultingBalance}")
                    SelectionContainer {
                        Text("Id: ${transaction.operationId}")
                    }
                }
            }
        }

        Button(
            onClick = { nav.navigateUp() },
            modifier = Modifier.padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(secondaryBtnColor())
        ) {
            Text("REGRESAR")
        }
    }
}

@Preview( showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, /*showBackground = true, backgroundColor = 0xFF121212*/)
@Composable
fun PreviewTransaction() {
    BankAppTheme {
        TransactionDetails(
            Transaction("00000000-0000000000", 999, 100, 200, LocalDateTime.now(), TransactionState.SUCCESS),
            Entry("00000000-0000000000", TransactionType.TRANSFER_IN, 999, 200, 200, LocalDateTime.now(), 1999, 1),
            EmptyDestinationsNavigator
        )
    }
}