package com.sjm.bankapp.screens.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.models.TransactionType
import com.sjm.bankapp.logic.models.dao.Entry
import com.sjm.bankapp.screens.Balance
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.Title
import com.sjm.bankapp.ui.theme.Green
import com.sjm.bankapp.ui.theme.Red
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import com.sjm.bankapp.ui.theme.strokeColor
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun History(vm: HistoryViewModel = viewModel(), nav: DestinationsNavigator) {
    val ls = rememberLazyListState()

    Base {

        Title("Historial de transacciones")

        Balance()

        LazyColumn(
            state = ls, verticalArrangement = Arrangement.Top, modifier = Modifier.weight(9F)
        ) {
            if (vm.history.isEmpty()) {
                item {
                    Text("No hay transacciones")
                }
            } else {
                items(vm.history, key = { it.id }) { t ->
                    EntryItem(t)
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

@Composable
fun EntryItem(entry: Entry) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, strokeColor()),
        elevation = 5.dp,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
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

fun getTypeColor(type: TransactionType) = when (type) {
    TransactionType.DEPOSIT -> Green
    TransactionType.WITHDRAW -> Red
    TransactionType.TRANSFER_OUT -> Red
    TransactionType.TRANSFER_IN -> Green
    TransactionType.BILL -> Red
}

