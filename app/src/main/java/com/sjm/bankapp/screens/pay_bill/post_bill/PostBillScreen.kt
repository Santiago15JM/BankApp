package com.sjm.bankapp.screens.pay_bill.post_bill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.dao.TransactionResponse
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.Card
import com.sjm.bankapp.screens.Subtitle
import com.sjm.bankapp.screens.Title
import com.sjm.bankapp.screens.destinations.HomeDestination
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun PostBillScreen(bill: Bill, response: TransactionResponse, nav: DestinationsNavigator) {
    Base {
        Title(text = "Factura pagada")

        Card(Modifier.padding(20.dp)) {
            Column(Modifier.padding(20.dp)) {
                Subtitle(text = "Detalles de la factura")
                Text(text = "Fecha: ${bill.date.format(DateTimeFormatter.ofPattern("d/M/y HH:mm:ss"))}")
                Text(text = "Cuenta origen: ${bill.customerId}")
                Text(text = "Id tienda: ${bill.shopId}")
                Text(text = "Estado: ${response.transactionResult}") //FIXME Always will be Succeeded, otherwise this screen wouldn't be called
                Text(text = "Id: ${response.transactionId}")
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
