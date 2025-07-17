package com.sjm.bankapp.screens.pay_bill

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.models.Business
import com.sjm.bankapp.screens.Balance
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.BottomButtonBar
import com.sjm.bankapp.screens.Card
import com.sjm.bankapp.screens.ConfirmDialog
import com.sjm.bankapp.screens.Subtitle
import com.sjm.bankapp.screens.Title
import com.sjm.bankapp.screens.destinations.PostBillScreenDestination
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import com.sjm.bankapp.ui.theme.textColor

@Destination
@Composable
fun PayBill(nav: DestinationsNavigator, vm: PayBillViewModel = viewModel()) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showBusinessesDialog by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        vm.fetchBusinesses()
    }

    Base {
        Title(text = "Pagar factura")

        Balance()

        Spacer(Modifier.weight(1F))

        Subtitle(
            modifier = Modifier.padding(20.dp), text = when (vm.state) {
                State.INITIAL -> "Ingresa los datos de la factura"
                State.FETCHING -> "Obteniendo factura"
                State.NOT_FOUND -> "Asegúrate de que los datos son correctos"
                else -> "Factura"
            }
        )

        Card(modifier = Modifier.padding(horizontal = 20.dp)) {
            Column(Modifier.padding(20.dp)) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp), onClick = {
                        showBusinessesDialog = true
                    }) {
                    Text(
                        text = if (vm.selectedBusiness == null) "Selecciona un establecimiento" else vm.selectedBusiness!!.name,
                        color = textColor()
                    )
                }

                OutlinedTextField(
                    value = vm.billCode,
                    label = {
                        Text(
                            "Referencia de la factura",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
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
                        "Valor: $${if (vm.bill != null && vm.billCode.isNotEmpty()) vm.bill?.cost else "N.A"}",
                        Modifier.padding(top = 10.dp)
                    )
                    if (vm.state == State.FETCHING) CircularProgressIndicator(Modifier.size(18.dp))
                }
            }
        }

        BottomButtonBar(
            onCancel = { nav.navigateUp() },
            acceptText = "PAGAR",
            onAccept = { showConfirmDialog = true },
            isAcceptEnabled = vm.shouldEnableButton()
        )

        if (showConfirmDialog) {
            ConfirmDialog(
                title = "¿Confirmas realizar el pago de la factura?",
                onDismissRequest = { showConfirmDialog = false },
                onAccept = {
                    vm.payBill(next = { bill, res ->
                        nav.navigate(PostBillScreenDestination(bill, res, vm.selectedBusiness!!.name))
                    })
                    showConfirmDialog = false
                }) {
                Text(
                    "Establecimiento: ${vm.selectedBusiness?.name}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "Costo: $${vm.bill?.cost}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (showBusinessesDialog) {
            businessesDialog(
                vm.businessList,
                onSelect = {
                    vm.selectedBusiness = it; showBusinessesDialog = false; vm.startFetchJob()
                },
                onDismiss = { showBusinessesDialog = false })
        }
    }
}

@Composable
fun businessesDialog(
    businessList: List<Business>, onSelect: (Business) -> Unit = {}, onDismiss: () -> Unit = {}
) {
    Dialog(onDismiss) {
        Card {
            Column(
                Modifier
                    .padding(20.dp)
                    .heightIn(max = 400.dp)
            ) {
                Text(
                    "Selecciona el proveedor",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(businessList) { business ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                color = secondaryBtnColor(),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = { onSelect(business) })
                            ) {
                                Text(
                                    business.name,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}