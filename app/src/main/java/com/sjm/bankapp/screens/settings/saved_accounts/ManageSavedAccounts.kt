package com.sjm.bankapp.screens.settings.saved_accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.models.SavedAccount
import com.sjm.bankapp.ui.Base
import com.sjm.bankapp.ui.BottomButtonBar
import com.sjm.bankapp.ui.Button
import com.sjm.bankapp.ui.Card
import com.sjm.bankapp.ui.Subtitle
import com.sjm.bankapp.ui.Title
import com.sjm.bankapp.ui.theme.secondaryBtnColor

//@RootNavGraph(start = true)
@Destination
@Composable
fun ManageSavedAccounts(vm: SavedAccountsViewModel = viewModel(), nav: DestinationsNavigator) {
    var openAddDialog by remember { mutableStateOf(false) }
    var openEditDialog by remember { mutableStateOf(false) }
    var openDeleteDialog by remember { mutableStateOf(false) }

    Base {
        Title("Cuentas guardadas")

        Spacer(modifier = Modifier.weight(0.4F))

        Card(
            Modifier
                .padding(horizontal = 20.dp)
                .weight(1F)
                .fillMaxWidth()
        ) {
            if (vm.savedAccounts.isEmpty()) {
                Text(
                    "No hay cuentas guardadas",
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(Modifier.padding(10.dp)) {
                    items(vm.savedAccounts) {
                        SavedAccountItem(account = it, {
                            vm.selectedAccount = it
                            openEditDialog = true
                        }, {
                            vm.selectedAccount = it
                            openDeleteDialog = true
                        })
                    }
                }
            }
        }

        BottomButtonBar(
            onCancel = { nav.navigateUp() },
            acceptText = "AGREGAR",
            onAccept = { openAddDialog = true },
        )

        when {
            openAddDialog -> {
                AddDialog(onDismissRequest = { openAddDialog = false }, onAccept = { desc, id ->
                    vm.addAccount(SavedAccount(id, desc))
                })
            }

            openEditDialog -> {
                EditDialog(vm.selectedAccount ?: SavedAccount(0, "Default"),
                    onDismissRequest = { openEditDialog = false },
                    onAccept = { desc, id -> vm.editAccount(desc, id) })
            }

            openDeleteDialog -> {
                DeleteDialog(selected = vm.selectedAccount ?: SavedAccount(0, "Default"),
                    onDismissRequest = { openDeleteDialog = false },
                    onAccept = {
                        vm.removeAccount()
                    })
            }
        }
    }
}

@Composable
fun SavedAccountItem(
    account: SavedAccount, onEditClick: () -> Unit, onDeleteClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Text(text = account.description)
            Text(text = "${account.aId}")
        }
        IconButton(onClick = { onEditClick() }) {
            Icon(Icons.Default.Edit, "Edit")
        }
        IconButton(onClick = { onDeleteClick() }) {
            Icon(Icons.Default.Delete, "Delete")
        }
    }
}

@Composable
fun AddDialog(
    onDismissRequest: () -> Unit, onAccept: (String, Long) -> Unit
) {
    var description by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(shape = RoundedCornerShape(10.dp)) {
            Column(Modifier.padding(20.dp)) {
                Subtitle("Agregar cuenta")

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                OutlinedTextField(
                    value = id,
                    onValueChange = { if (it.isDigitsOnly()) id = it },
                    label = { Text("Id") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { onDismissRequest() },
                        colors = ButtonDefaults.buttonColors(secondaryBtnColor())
                    ) {
                        Text("CANCELAR")
                    }
                    Spacer(modifier = Modifier.width(20.dp))

                    Button(modifier = Modifier.weight(1f),
                        enabled = description.isNotBlank() and id.isNotEmpty(),
                        onClick = {
                            onAccept(description, id.toLong())
                            onDismissRequest()
                        }) {
                        Text("AGREGAR")
                    }
                }
            }
        }
    }
}

@Composable
fun EditDialog(
    account: SavedAccount, onDismissRequest: () -> Unit, onAccept: (String, Long) -> Unit
) {
    var description by remember { mutableStateOf(account.description) }
    var id by remember { mutableStateOf(account.aId.toString()) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card {
            Column(Modifier.padding(20.dp)) {
                Subtitle("Editar cuenta")

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                OutlinedTextField(
                    value = id,
                    onValueChange = { if (it.isDigitsOnly()) id = it },
                    label = { Text("Id") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(colors = ButtonDefaults.buttonColors(secondaryBtnColor()),
                        modifier = Modifier.weight(1f),
                        onClick = { onDismissRequest() }) {
                        Text("CANCELAR")
                    }
                    Spacer(modifier = Modifier.width(14.dp))

                    Button(modifier = Modifier.weight(1f),
                        enabled = description.isNotBlank() and id.isNotEmpty(),
                        onClick = {
                            onAccept(description, id.toLong())
                            onDismissRequest()
                        }) {
                        Text("ACEPTAR")
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteDialog(
    selected: SavedAccount,
    onDismissRequest: () -> Unit,
    onAccept: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card {
            Column(Modifier.padding(20.dp)) {
                Subtitle("Eliminar ${selected.description}")

                Text(
                    "¿Seguro que deseas eliminar esta cuenta?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))

                Row {
                    Button(
                        onClick = { onDismissRequest() },
                        colors = ButtonDefaults.buttonColors(secondaryBtnColor()),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("CANCELAR")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { onAccept() }, modifier = Modifier.weight(1f)
                    ) {
                        Text("ELIMINAR")
                    }
                }
            }
        }
    }
}
