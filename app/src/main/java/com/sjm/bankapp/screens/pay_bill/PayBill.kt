package com.sjm.bankapp.screens.pay_bill

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInQuint
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.SoundManager
import com.sjm.bankapp.logic.models.Business
import com.sjm.bankapp.navigation.NavType
import com.sjm.bankapp.navigation.PostBillKey
import com.sjm.bankapp.ui.Balance
import com.sjm.bankapp.ui.Base
import com.sjm.bankapp.ui.BottomButtonBar
import com.sjm.bankapp.ui.Card
import com.sjm.bankapp.ui.ConfirmDialog
import com.sjm.bankapp.ui.GenericDialog
import com.sjm.bankapp.ui.Subtitle
import com.sjm.bankapp.ui.Title
import com.sjm.bankapp.ui.errorText
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import com.sjm.bankapp.ui.theme.textColor

@Composable
fun PayBill(
    navigateTo: (NavType) -> Unit,
    navigateBack: () -> Unit,
    vm: PayBillViewModel = viewModel()
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showBusinessesDialog by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var error = 0

    var animate by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.fetchBusinesses()
        animate = true
    }

    val enterAnimation =
        fadeIn(tween(durationMillis = 300)) + scaleIn(
            animationSpec = tween(
                300,
                easing = EaseOutQuint
            ), initialScale = 0.8F
        )

    val elevation by animateDpAsState(
        if (animate) 8.dp else 0.dp,
        animationSpec = tween(400, easing = EaseInQuint)
    )

    Base {
        Title(text = "Pagar factura")

        Balance { BankEnd.getBalance().toString() }

        Spacer(Modifier.weight(1F))

        Subtitle(
            modifier = Modifier.padding(20.dp), text = when (vm.state) {
                State.INITIAL -> "Ingresa los datos de la factura"
                State.FETCHING -> "Obteniendo factura"
                State.NOT_FOUND -> "Asegúrate de que los datos son correctos"
                else -> "Factura"
            }
        )

        AnimatedVisibility(animate, enter = enterAnimation) {
            Card(modifier = Modifier.padding(horizontal = 20.dp), elevation = elevation) {
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
        }

        BottomButtonBar(
            onCancel = navigateBack,
            acceptText = "PAGAR",
            onAccept = { showConfirmDialog = true },
            isAcceptEnabled = vm.shouldEnableButton()
        )

        when {
            showConfirmDialog -> ConfirmDialog(
                title = "¿Confirmas realizar el pago de la factura?",
                onDismissRequest = { showConfirmDialog = false },
                onAccept = {
                    vm.payBill(
                        next = { bill, res ->
                            navigateTo(PostBillKey(bill, res, vm.selectedBusiness!!.name))
                        },
                        onError = { code ->
                            error = code
                            showError = true
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

            showError -> {
                GenericDialog(
                    text = "Ocurrió un error",
                    subtext = errorText(error),
                    onDismissRequest = { showError = false })
                SoundManager.play(SoundManager.Sounds.ATTENTION)
            }
        }

        if (showBusinessesDialog) {
            BusinessesDialog(
                vm.businessList,
                onSelect = {
                    vm.selectedBusiness = it; showBusinessesDialog = false; vm.startFetchJob()
                },
                onDismiss = { showBusinessesDialog = false })
        }
    }
}

@Composable
fun BusinessesDialog(
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