package com.sjm.bankapp.screens.pay_bill

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.screens.Balance
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.BottomButtonBar
import com.sjm.bankapp.screens.ConfirmDialog
import com.sjm.bankapp.screens.Subtitle
import com.sjm.bankapp.screens.Title
import com.sjm.bankapp.screens.destinations.PostBillScreenDestination
import com.sjm.bankapp.ui.theme.strokeColor

//@RootNavGraph(start = true)
@Destination
@Composable
fun PayBill(nav: DestinationsNavigator, vm: PayBillViewModel = viewModel()) {
    var showConfirmDialog by remember { mutableStateOf(false) }

    Base {

        Title(text = "Pagar factura")
        Balance { BankEnd.getBalance() }

        //TODO: Improve UI

        Spacer(Modifier.weight(1F))

        Subtitle(
            modifier = Modifier.padding(20.dp), text = when {
                vm.shopID.isEmpty() or vm.billCode.isEmpty() -> "Ingresa los datos de la factura"
                !vm.isQuoteObtained -> "Obteniendo factura"
                vm.isQuoteObtained -> "Asegúrate de que los datos son correctos"
                else -> "Factura"
            }
        )

        Surface(
            Modifier
                .wrapContentHeight()
                .padding(horizontal = 20.dp),
            border = BorderStroke(1.dp, strokeColor()),
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp
        ) {
            Column(Modifier.padding(20.dp)) {

                OutlinedTextField(
                    value = vm.shopID,
                    label = { Text("Codigo del establecimiento") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                    ),
                    onValueChange = {
                        if (it.isDigitsOnly()) vm.shopID = it
                        vm.startFetchJob()
                    },
                )
                OutlinedTextField(
                    value = vm.billCode,
                    label = { Text("Codigo de la factura") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        if (it.isDigitsOnly()) vm.billCode = it
                        vm.startFetchJob()
                    },
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Valor: $${if (vm.cost != 0) vm.cost else "N.A"}",
                        Modifier.padding(top = 10.dp)
                    )
                    if (vm.isFetchingQuote) CircularProgressIndicator(Modifier.size(18.dp))
                }
            }
        }

        BottomButtonBar(onCancel = { nav.navigateUp() },
            acceptText = "PAGAR",
            onAccept = { showConfirmDialog = true },
            isAcceptEnabled = vm.shouldEnableButton()
        )

        if (showConfirmDialog) {
            ConfirmDialog(title = "¿Confirmas el pago de la factura?",
                onDismissRequest = { showConfirmDialog = false },
                onAccept = {
                    vm.onPayBill(next = { bill, res ->
                        nav.navigate(PostBillScreenDestination(bill, res))
                    })
                    Log.wtf("PAYBILL", "UH, WTF???")
//                showConfirmDialog = false
//                    nav.navigateUp() //TODO if status is fulfilled
                }) {
                Text(
                    "Tienda: ${vm.quote?.shopName}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "Costo: $${vm.quote?.cost}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
