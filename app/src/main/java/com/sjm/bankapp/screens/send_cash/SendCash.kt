package com.sjm.bankapp.screens.send_cash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
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
import androidx.navigation3.runtime.NavBackStack
import com.sjm.bankapp.logic.models.SavedAccount
import com.sjm.bankapp.navigation.PostPaymentKey
import com.sjm.bankapp.ui.Balance
import com.sjm.bankapp.ui.Base
import com.sjm.bankapp.ui.BottomButtonBar
import com.sjm.bankapp.ui.Card
import com.sjm.bankapp.ui.ConfirmDialog
import com.sjm.bankapp.ui.GenericDialog
import com.sjm.bankapp.ui.Subtitle
import com.sjm.bankapp.ui.Title
import com.sjm.bankapp.ui.errorText
import com.sjm.bankapp.ui.theme.Black
import com.sjm.bankapp.ui.theme.accentColor
import com.sjm.bankapp.ui.theme.strokeColor

@Composable
fun SendCash(navStack: NavBackStack, vm: SendCashViewModel = viewModel()) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var error = 0

    Base {
        Title(text = "Enviar dinero")

        Balance()

        Spacer(Modifier.weight(0.3F))

        Subtitle(text = "Cuentas", modifier = Modifier.padding(20.dp))

        TypeOfAccountSelector(
            vm,
            Modifier
                .weight(1F)
                .padding(horizontal = 20.dp)
        )

        BottomButtonBar(
            onCancel = { navStack.removeLastOrNull() },
            acceptText = "ENVIAR",
            isAcceptEnabled = vm.shouldEnableButton(),
            onAccept = {
                showConfirmDialog = true
            },
        )

        when {
            showConfirmDialog -> {
                ConfirmDialog(title = "¿Confirmas esta transferencia?", onAccept = {
                    vm.onSendTransaction(onSuccess = { t ->
                        showConfirmDialog = false
                        navStack.add(PostPaymentKey(t))
                    }, onError = { code ->
                        showConfirmDialog = false
                        error = code
                        showError = true
                    })
                }, onDismissRequest = { showConfirmDialog = false }) {
                    Text(
                        "Destino: ${if (vm.usingSavedAccount) vm.selectedAccount.value.description else vm.receiverID}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "Monto: $${vm.amount}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            showError -> {
                GenericDialog(text = errorText(error), onDismissRequest = { showError = false })
            }
        }
    }
}

@Composable
fun TypeOfAccountSelector(
    vm: SendCashViewModel, modifier: Modifier = Modifier
) {
    Card(modifier) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = if (vm.usingSavedAccount) accentColor() else MaterialTheme.colors.surface,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clickable {
                            vm.usingSavedAccount = true
                        },
                ) {
                    Text(
                        "Guardada",
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center,
                        color = if (vm.usingSavedAccount) Black else MaterialTheme.colors.onSurface
                    )
                }
                Surface(
                    color = if (!vm.usingSavedAccount) accentColor() else MaterialTheme.colors.surface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            vm.usingSavedAccount = false
                        },
                ) {
                    Text(
                        "No guardada",
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center,
                        color = if (!vm.usingSavedAccount) Black else MaterialTheme.colors.onSurface
                    )
                }
            }
            Divider(color = strokeColor())

            when {
                !vm.usingSavedAccount -> NonSavedPanel(vm)
                vm.savedAccounts.isEmpty() -> Text(
                    "No tienes cuentas guardadas", Modifier.padding(20.dp)
                )

                else -> SavedAccountsPanel(vm)
            }
        }
    }
}

@Composable
fun SavedAccountsPanel(vm: SendCashViewModel) {
    OutlinedTextField(
        vm.amount,
        { if (it.isDigitsOnly()) vm.amount = it },
        label = { Text("Monto") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
    )
    SavedAccountsList(vm.savedAccounts, vm.selectedAccount.value, onSelect = {
        vm.selectedAccount.value = it
    })
}

@Composable
fun SavedAccountsList(
    savedAccounts: MutableList<SavedAccount>,
    selectedAccount: SavedAccount,
    onSelect: (SavedAccount) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        RadioGroup(
            savedAccounts, selection = selectedAccount
        ) {
            onSelect(it)
        }
    }
}

@Composable
fun RadioGroup(
    items: List<SavedAccount>, selection: SavedAccount, onItemClick: ((SavedAccount) -> Unit)
) {
    LazyColumn {
        items(items) { item ->
            SavedAccountItem(text = item.description, selected = item == selection) {
                onItemClick(item)
            }
        }
    }
}

@Composable
fun SavedAccountItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text)

        RadioButton(
            selected = selected,
            onClick = { onClick() },
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary)
        )
    }
}

@Composable
fun NonSavedPanel(vm: SendCashViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            OutlinedTextField(
                value = vm.amount,
                onValueChange = { if (it.isDigitsOnly()) vm.amount = it },
                label = { Text("Monto") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = vm.receiverID,
                onValueChange = { if (it.isDigitsOnly()) vm.receiverID = it },
                label = { Text("Cuenta destino") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            AnimatedVisibility(vm.saveAccount) {
                OutlinedTextField(
                    value = vm.description,
                    onValueChange = { vm.description = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿Guardar cuenta?")
            Switch(
                vm.saveAccount,
                { vm.saveAccount = !vm.saveAccount },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.primary,
                    checkedTrackColor = MaterialTheme.colors.primaryVariant,
                )
            )
        }
    }
}
