package com.sjm.bankapp.screens.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.Preferences
import com.sjm.bankapp.screens.Base
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.GenericDialog
import com.sjm.bankapp.screens.LoadingDialog
import com.sjm.bankapp.screens.MenuOption
import com.sjm.bankapp.screens.OptionsCard
import com.sjm.bankapp.screens.Title
import com.sjm.bankapp.screens.destinations.LoginDestination
import com.sjm.bankapp.screens.destinations.ManageSavedAccountsDestination
import com.sjm.bankapp.screens.settings.change_account_info.ChangeEmailDialog
import com.sjm.bankapp.screens.settings.change_account_info.ChangePasswordDialog
import com.sjm.bankapp.screens.settings.change_account_info.ChangePhoneDialog
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import kotlinx.coroutines.runBlocking

@Destination
@Composable
fun Settings(navigator: DestinationsNavigator, vm: SettingsViewModel = viewModel()) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Base {
        Title(text = "Opciones")

        Spacer(Modifier.weight(1f))

        OptionsCard {
            MenuOption(text = "Cuentas guardadas",
                onClick = { navigator.navigate(ManageSavedAccountsDestination) })
            MenuOption(text = "Cambiar correo", onClick = { vm.showChangeEmail = true })
            MenuOption(text = "Cambiar teléfono", onClick = { vm.showChangePhone = true })
            MenuOption(text = "Cambiar contraseña", onClick = { vm.showChangePassword = true })
            MenuOption(text = "Soporte",
                onClick = { uriHandler.openUri("http://bankapp.com/support/") })
        }

        Button(
            onClick = { navigator.navigateUp() },
            colors = ButtonDefaults.buttonColors(secondaryBtnColor())
        ) {
            Text(text = "REGRESAR")
        }
        Spacer(Modifier.height(30.dp))

        when {
            vm.showChangeEmail -> {
                val currentEmail = runBlocking { Preferences.getUserEmail(context)!! }
                ChangeEmailDialog(
                    currentEmail = currentEmail,
                    onDismissRequest = { vm.showChangeEmail = false },
                    onAccept = { email ->
                        vm.changeEmail(email, context)
                    },
                )
            }

            vm.showChangePhone -> {
                val currentPhone = runBlocking { Preferences.getUserPhone(context)!! }
                ChangePhoneDialog(currentPhone = currentPhone,
                    onDismissRequest = { vm.showChangePhone = false },
                    onAccept = { phone ->
                        vm.changePhone(phone, context)
                    })
            }

            vm.showChangePassword -> ChangePasswordDialog(
                onDismissRequest = { vm.showChangePassword = false },
                onAccept = { oldp, newp ->
                    vm.changePassword(oldp, newp)
                },
            )

            vm.showLoading -> {
                LoadingDialog()
            }

            vm.showNetworkError -> GenericDialog(text = "Hubo un error con la red") {
                vm.showNetworkError = false
            }

            vm.showError -> GenericDialog(text = "No se pudo realizar el cambio") {
                vm.showError = false
            }

            vm.showSuccess && vm.logout -> GenericDialog(
                "Se realizó el cambio",
                "Debes volver a iniciar sesión"
            ) {
                vm.showSuccess = false
                navigator.popBackStack(LoginDestination, false)
            }

            vm.showSuccess -> GenericDialog("Se realizó el cambio") {
                vm.showSuccess = false
            }
        }
    }
}
