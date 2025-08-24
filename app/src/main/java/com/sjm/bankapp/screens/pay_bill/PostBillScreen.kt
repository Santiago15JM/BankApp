package com.sjm.bankapp.screens.pay_bill

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInExpo
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sjm.bankapp.logic.Session
import com.sjm.bankapp.logic.SoundManager
import com.sjm.bankapp.logic.dto.transaction.TransactionResponse
import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.BillState
import com.sjm.bankapp.ui.Base
import com.sjm.bankapp.ui.Button
import com.sjm.bankapp.ui.Card
import com.sjm.bankapp.ui.Subtitle
import com.sjm.bankapp.ui.Title
import com.sjm.bankapp.ui.theme.BankAppTheme
import com.sjm.bankapp.ui.theme.accentColor
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import com.sjm.bankapp.ui.theme.textColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PostBillScreen(
    bill: Bill,
    transaction: TransactionResponse,
    businessName: String,
    returnHome: () -> Unit
) {
    var animate by remember { mutableStateOf(false) }
    val enterAnimation =
        scaleIn(initialScale = 0.8F) + fadeIn(animationSpec = tween(easing = EaseInExpo))
    LaunchedEffect(Unit) {
        animate = true
        SoundManager.play(SoundManager.Sounds.SUCCESS)
    }

    Base {
        Title(text = "Pago enviado")

        AnimatedVisibility(
            animate,
            enter = enterAnimation
        ) {
            Column(verticalArrangement = Arrangement.Bottom) {
                Subtitle(
                    "El pago puede tardar hasta 3 dias en ser procesado",
                    color = textColor(),
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
                            Text(
                                text = "Fecha: ${
                                    transaction.date.format(
                                        DateTimeFormatter.ofPattern(
                                            "d/M/y HH:mm:ss"
                                        )
                                    )
                                }"
                            )
                            Text(text = "Cuenta origen: ${Session.accountId}")
                            Text(text = "Establecimiento: $businessName")
                            Text(text = "Referencia de factura: ${bill.serviceId}")
                            Text(text = "Valor: $${bill.cost}")
                            Text(text = "Estado: ${bill.status.state}")
                            Text(text = "Id: ${transaction.operationId}")
                        }
                    }
                }
            }
        }

        Button(
            onClick = returnHome,
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
            "Empresa Co.", {}
        )
    }
}