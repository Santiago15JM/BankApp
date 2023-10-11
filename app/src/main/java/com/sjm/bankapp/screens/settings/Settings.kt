package com.sjm.bankapp.screens.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.Server
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.MenuOption
import com.sjm.bankapp.screens.OptionsCard
import com.sjm.bankapp.screens.Title
import com.sjm.bankapp.screens.destinations.ManageSavedAccountsDestination
import com.sjm.bankapp.screens.settings.change_account_info.ChangeEmailDialog
import com.sjm.bankapp.screens.settings.change_account_info.ChangePhoneDialog
import com.sjm.bankapp.ui.theme.secondaryBtnColor

@Destination
@Composable
fun Settings(navigator: DestinationsNavigator) {
    var showChangeEmail by remember { mutableStateOf(false) }
    var showChangePhone by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    Base {
        Title(text = "Opciones")

        Spacer(Modifier.weight(1f))

        OptionsCard {
            MenuOption(text = "Cuentas guardadas",
                onClick = { navigator.navigate(ManageSavedAccountsDestination) })
            MenuOption(text = "Cambiar correo", onClick = { showChangeEmail = true })
            MenuOption(text = "Cambiar teléfono", onClick = { showChangePhone = true })
            MenuOption(text = "Soporte", onClick = {
                uriHandler.openUri("http://bankapp.com/support/")
            })
        }

        Button(
            onClick = { navigator.navigateUp() },
            colors = ButtonDefaults.buttonColors(secondaryBtnColor())
        ) {
            Text(text = "REGRESAR")
        }
        Spacer(Modifier.height(30.dp))

        when {
            showChangeEmail -> ChangeEmailDialog(onDismissRequest = { showChangeEmail = false },
                onAccept = { email -> Server.changeEmail(email) })

            showChangePhone -> ChangePhoneDialog(
                onDismissRequest = { showChangePhone = false },
                onAccept = { phone -> Server.changePhone(phone)}
            )
        }
    }
}
