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
import com.sjm.bankapp.logic.models.Transaction
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.Card
import com.sjm.bankapp.screens.Subtitle
import com.sjm.bankapp.screens.Title
import com.sjm.bankapp.screens.destinations.HomeDestination
import com.sjm.bankapp.ui.theme.BankAppTheme
import com.sjm.bankapp.ui.theme.Black
import com.sjm.bankapp.ui.theme.accentColor
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun PostPaymentScreen(
    transaction: Transaction,
    nav: DestinationsNavigator,
) {
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
                    Text(text = "Cuenta origen: ${transaction.senderId}")
                    Text(text = "Cuenta destino: ${transaction.receiverId}")
                    SelectionContainer {
                        Text("Id: ${transaction.operationId}")
                    }
                }
            }
        }

        Button(
            onClick = { nav.popBackStack(HomeDestination, false) },
            modifier = Modifier.padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(secondaryBtnColor())
        ) {
            Text("REGRESAR")
        }
    }
}

//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES /*showBackground = true, backgroundColor = 0xFF121212*/)
//@Composable
//fun PreviewPostPayment() {
//    BankAppTheme {
//        PostPaymentScreen(
//            Transaction("00000000-0000000000", 999, 100, 200, LocalDateTime.now()),
//            EmptyDestinationsNavigator
//        )
//    }
//}