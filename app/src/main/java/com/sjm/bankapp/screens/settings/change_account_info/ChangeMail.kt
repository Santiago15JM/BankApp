package com.sjm.bankapp.screens.settings.change_account_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.Card
import com.sjm.bankapp.screens.Subtext
import com.sjm.bankapp.screens.Subtitle
import com.sjm.bankapp.ui.theme.emphasisTextColor
import com.sjm.bankapp.ui.theme.secondaryBtnColor

@Composable
fun ChangeEmailDialog(
    currentEmail: String,
    onDismissRequest: () -> Unit,
    onAccept: (String) -> Unit,
) {
    var newMail by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(shape = RoundedCornerShape(10.dp)) {
            Column(Modifier.padding(20.dp)) {
                Subtitle("Cambiar correo")

                Subtext("Esto cerrará tu sesión.", color = emphasisTextColor())

                Subtext("Correo actual: $currentEmail")

                OutlinedTextField(
                    value = newMail,
                    onValueChange = { newMail = it },
                    label = { Text("Correo nuevo") },
                    singleLine = true,
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
                        enabled = newMail.isValidEmail() && newMail != currentEmail,
                        onClick = {
                            onAccept(newMail)
                            onDismissRequest()
                        }) {
                        Text("CAMBIAR")
                    }
                }
            }
        }
    }
}

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}