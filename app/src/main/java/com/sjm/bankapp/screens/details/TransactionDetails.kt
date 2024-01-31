package com.sjm.bankapp.screens.details

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
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.models.Entry
import com.sjm.bankapp.logic.models.Transaction
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.Card
import com.sjm.bankapp.screens.Subtitle
import com.sjm.bankapp.screens.Title
import com.sjm.bankapp.ui.theme.Black
import com.sjm.bankapp.ui.theme.accentColor
import com.sjm.bankapp.ui.theme.secondaryBtnColor
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
                    Text("Cuenta origen: ${transaction.senderId}")
                    Text("Cuenta destino: ${transaction.receiverId}")
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
