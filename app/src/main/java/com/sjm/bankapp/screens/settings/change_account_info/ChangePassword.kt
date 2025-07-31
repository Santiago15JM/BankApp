package com.sjm.bankapp.screens.settings.change_account_info

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sjm.bankapp.ui.Button
import com.sjm.bankapp.ui.Card
import com.sjm.bankapp.ui.PasswordTextField
import com.sjm.bankapp.ui.Subtext
import com.sjm.bankapp.ui.Subtitle
import com.sjm.bankapp.ui.theme.emphasisTextColor
import com.sjm.bankapp.ui.theme.secondaryBtnColor

@Composable
fun ChangePasswordDialog(
    onDismissRequest: () -> Unit,
    onAccept: (String, String) -> Unit,
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPassword2 by remember { mutableStateOf("") }
    val c = LocalContext.current

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(shape = RoundedCornerShape(10.dp)) {
            Column(Modifier.padding(20.dp)) {
                Subtitle("Cambiar contraseña")

                Subtext("Esto cerrará tu sesión.", color = emphasisTextColor())

                PasswordTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = "Contraseña anterior",
                    imeAction = ImeAction.Next
                )

                PasswordTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = "Nueva contraseña",
                    imeAction = ImeAction.Next
                )

                PasswordTextField(value = newPassword2,
                    onValueChange = { newPassword2 = it },
                    label = "Confirma contraseña",
                    imeAction = ImeAction.Done,
                    onDone = {
                        validatePassword(c, oldPassword, newPassword, newPassword2) {
                            onAccept(oldPassword, newPassword2)
                            onDismissRequest()
                        }
                    })

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
                        enabled = newPassword.isNotBlank(),
                        onClick = {
                            validatePassword(c, oldPassword, newPassword, newPassword2) {
                                onAccept(oldPassword, newPassword2)
                                onDismissRequest()
                            }
                        }) {
                        Text("CAMBIAR")
                    }
                }
            }
        }
    }
}

private fun validatePassword(
    c: Context,
    oldPassword: String,
    newPassword: String,
    newPassword2: String,
    onValid: () -> Unit,
) {
    if (newPassword != newPassword2) {
        Toast.makeText(
            c, "Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT
        ).show()
    } else if (oldPassword == newPassword) {
        Toast.makeText(
            c, "La nueva contraseña no puede ser igual a la anterior", Toast.LENGTH_SHORT
        ).show()
    } else {
        onValid()
    }
}