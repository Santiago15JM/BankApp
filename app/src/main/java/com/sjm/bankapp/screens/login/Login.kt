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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.R
import com.sjm.bankapp.screens.Button
import com.sjm.bankapp.screens.VisibilityIcon
import com.sjm.bankapp.ui.theme.SurfaceDark
import com.sjm.bankapp.ui.theme.SurfaceLight

@RootNavGraph(start = true)
@Destination
@Composable
fun Login(navigator: DestinationsNavigator, loginVM: LoginViewModel = viewModel()) {
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

        OutlinedTextField(value = loginVM.user,
            onValueChange = { loginVM.updateUser(it) },
            label = { Text("Usuario") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        var passwordVisibility by remember { mutableStateOf(false) }
        OutlinedTextField(value = loginVM.password,
            onValueChange = { loginVM.updatePassword(it) },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onDone = { loginVM.logIn(navigator, c) }),
            trailingIcon = {

                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    VisibilityIcon(show = passwordVisibility)
                }
            })

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = { loginVM.logIn(navigator, c) }, contentPadding = PaddingValues(20.dp, 10.dp)
        ) {
            Text("INGRESAR")
        }
    }
}


