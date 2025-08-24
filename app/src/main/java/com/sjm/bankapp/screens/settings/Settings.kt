package com.sjm.bankapp.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInQuint
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sjm.bankapp.logic.Preferences
import com.sjm.bankapp.logic.Session
import com.sjm.bankapp.navigation.ManageContactsKey
import com.sjm.bankapp.navigation.NavType
import com.sjm.bankapp.screens.settings.change_account_info.ChangeEmailDialog
import com.sjm.bankapp.screens.settings.change_account_info.ChangePasswordDialog
import com.sjm.bankapp.screens.settings.change_account_info.ChangePhoneDialog
import com.sjm.bankapp.ui.Base
import com.sjm.bankapp.ui.Button
import com.sjm.bankapp.ui.GenericDialog
import com.sjm.bankapp.ui.LoadingDialog
import com.sjm.bankapp.ui.MenuOption
import com.sjm.bankapp.ui.OptionsCard
import com.sjm.bankapp.ui.Subtitle
import com.sjm.bankapp.ui.Title
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import kotlinx.coroutines.runBlocking

@Composable
fun Settings(
    navigateTo: (NavType) -> Unit,
    navigateBack: () -> Unit,
    returnToLogin: () -> Unit,
    vm: SettingsViewModel = viewModel()
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    var animate by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
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
        Title(text = "Opciones")

        Spacer(Modifier.weight(1f))

        Subtitle(text = "Tu cuenta:\n${Session.accountId}")

        AnimatedVisibility(
            visible = animate, enter = enterAnimation
        ) {
            OptionsCard(elevation = elevation) {
                MenuOption(
                    text = "Cuentas guardadas",
                    onClick = { navigateTo(ManageContactsKey) })
                MenuOption(text = "Cambiar correo", onClick = { vm.showChangeEmail = true })
                MenuOption(text = "Cambiar teléfono", onClick = { vm.showChangePhone = true })
                MenuOption(text = "Cambiar contraseña", onClick = { vm.showChangePassword = true })
                MenuOption(
                    text = "Soporte",
                    onClick = { uriHandler.openUri("http://bankapp.com/support/") })
            }
        }

        Button(
            onClick = navigateBack,
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
                ChangePhoneDialog(
                    currentPhone = currentPhone,
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
                returnToLogin()
            }

            vm.showSuccess -> GenericDialog("Se realizó el cambio") {
                vm.showSuccess = false
            }
        }
    }
}
