package com.sjm.bankapp.screens.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import com.sjm.bankapp.logic.models.Entry
import com.sjm.bankapp.logic.models.TransactionType
import com.sjm.bankapp.navigation.TransactionDetailsKey
import com.sjm.bankapp.ui.Balance
import com.sjm.bankapp.ui.Base
import com.sjm.bankapp.ui.Button
import com.sjm.bankapp.ui.Card
import com.sjm.bankapp.ui.Title
import com.sjm.bankapp.ui.theme.Green
import com.sjm.bankapp.ui.theme.Red
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import com.sjm.bankapp.ui.theme.textColor
import java.time.format.DateTimeFormatter

@Composable
fun History(navStack: NavBackStack, vm: HistoryViewModel = viewModel()) {
    val ls = rememberLazyListState()

    Base {

        Title("Historial de transacciones")

        Balance()

        LazyColumn(
            state = ls,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(9F)
        ) {
            if (vm.history.isNotEmpty()) {
                items(vm.history, key = { it.id }) { e ->
                    EntryItem(e) {
                        vm.getTransactionDetails(e.operationId, nextScreen = { t ->
                            navStack.add(TransactionDetailsKey(t, e))
                        })
                    }
                }
                item {
                    when {
                        vm.loading -> CircularProgressIndicator()

                        vm.failed -> {
                            LastHistoryItem("Hubo un error obteniendo transacciones anteriores")
                            TextButton(onClick = { vm.loadMore() }) {
                                Text("Cargar más")
                            }
                        }

                        !vm.endOfHistory ->
                            TextButton(onClick = { vm.loadMore() }, Modifier.padding(10.dp)) {
                            Text("Cargar más", color = textColor())
                        }

                        else -> LastHistoryItem("Fin del historial")
                    }
                }
            } else {
                item {
                    when {
                        !vm.loadedInitial -> CircularProgressIndicator()

                        vm.failed -> Text("Hubo un error obteniendo tus transacciones")

                        vm.history.isEmpty() -> Text("No hay transacciones")
                    }
                }
            }
        }

        Button(
            onClick = { navStack.removeLastOrNull() },
            modifier = Modifier.padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(secondaryBtnColor())
        ) {
            Text("REGRESAR")
        }
    }
}

@Composable
fun EntryItem(entry: Entry, onClick: () -> Unit) {
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .clickable { onClick() },
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Text(entry.type.text)
                Text(
                    text = "${entry.amount}",
                    color = getTypeColor(entry.type),
                )
            }
            Row {
                Text(entry.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm")))
            }
        }
    }
}

@Composable
fun LastHistoryItem(text: String) {
    Card(
        elevation = 5.dp,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text)
        }
    }
}

fun getTypeColor(type: TransactionType) = when (type) {
    TransactionType.DEPOSIT -> Green
    TransactionType.WITHDRAW -> Red
    TransactionType.TRANSFER_OUT -> Red
    TransactionType.TRANSFER_IN -> Green
    TransactionType.BILL -> Red
}

