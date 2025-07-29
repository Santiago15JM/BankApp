package com.sjm.bankapp.screens.pay_bill.post_bill

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.sjm.bankapp.logic.LocalStorage
import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.BillState
import com.sjm.bankapp.logic.models.dto.TransactionResponse
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.Card
import com.sjm.bankapp.screens.Subtitle
import com.sjm.bankapp.screens.Title
import com.sjm.bankapp.screens.destinations.HomeDestination
import com.sjm.bankapp.ui.theme.BankAppTheme
import com.sjm.bankapp.ui.theme.accentColor
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun PostBillScreen(bill: Bill, transaction: TransactionResponse, businessName: String, nav: DestinationsNavigator) {
    Base {
        Title(text = "Pago enviado")

        Column(verticalArrangement = Arrangement.Bottom) {
            Subtitle(
                "El pago puede tardar hasta 3 dias en ser procesado",
                modifier = Modifier.padding(20.dp)
            )

            Card(Modifier.padding(20.dp)) {
                Column {
                    Row(
                        Modifier
                            .background(accentColor())
                            .padding(16.dp)
                    ) {
                        Subtitle(text = "Detalles de la factura")
                    }
                    Column(
                        Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = "Fecha: ${transaction.date.format(DateTimeFormatter.ofPattern("d/M/y HH:mm:ss"))}")
                        Text(text = "Cuenta origen: ${LocalStorage.accountId}")
                        Text(text = "Establecimiento: $businessName")
                        Text(text = "Referencia de factura: ${bill.serviceId}")
                        Text(text = "Estado: ${bill.status.state}")
                        Text(text = "Id: ${transaction.operationId}")
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

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
fun PreviewPostPayment() {
    BankAppTheme {
        PostBillScreen(
            Bill(2694128555208484488, 3, "2", 43395, BillState.PENDING),
            TransactionResponse("00000000-0000000000", LocalDateTime.now(), 200000),
            "Empresa Co.",
            EmptyDestinationsNavigator
        )
    }
}