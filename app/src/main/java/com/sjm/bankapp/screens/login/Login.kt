package com.sjm.bankapp.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.R
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.GenericDialog
import com.sjm.bankapp.screens.LoadingDialog
import com.sjm.bankapp.screens.PasswordTextField
import com.sjm.bankapp.screens.destinations.HomeDestination
import com.sjm.bankapp.ui.theme.SurfaceDark
import com.sjm.bankapp.ui.theme.SurfaceLight

@RootNavGraph(start = true)
@Destination
@Composable
fun Login(navigator: DestinationsNavigator, vm: LoginViewModel = viewModel()) {
    val c = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.login_bk),
                contentScale = ContentScale.FillWidth,
                colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(SurfaceDark)
                else ColorFilter.tint(SurfaceLight)
            )
    ) {
        Image(
            painterResource(
                if (isSystemInDarkTheme()) R.drawable.icon_dark else R.drawable.icon_light
            ),
            "",
            Modifier.size(180.dp),
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "INICIO DE SESION",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
        )


        Spacer(Modifier.height(20.dp))

        OutlinedTextField(value = vm.email,
            onValueChange = { vm.email = it },
            label = { Text("Usuario") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        PasswordTextField(value = vm.password,
            onValueChange = { vm.password = it },
            label = "Contraseña",
            onDone = { vm.logIn(navigator, c) })

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = { vm.logIn(navigator, c) }, contentPadding = PaddingValues(20.dp, 10.dp)
        ) {
            Text("INGRESAR")
        }
        Button(
            onClick = { navigator.navigate(HomeDestination) },
            contentPadding = PaddingValues(20.dp, 10.dp)
        ) {
            Text("DEV")
        }

        when {
            vm.showLoading -> LoadingDialog()

            vm.showNetworkError -> GenericDialog("Hubo un error con la red", icon = Icons.Default.Warning) {
                vm.showNetworkError = false
            }
            
            vm.showBadCredentials -> GenericDialog("Credenciales incorrectas", icon = Icons.Default.Warning) {
                vm.showBadCredentials = false
            }
        }
    }
}
