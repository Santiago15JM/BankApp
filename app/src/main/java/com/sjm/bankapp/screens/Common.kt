package com.sjm.bankapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sjm.bankapp.R
import com.sjm.bankapp.ui.theme.SurfaceDark
import com.sjm.bankapp.ui.theme.SurfaceLight
import com.sjm.bankapp.ui.theme.secondaryBtnColor
import com.sjm.bankapp.ui.theme.strokeColor

@Composable
fun Base(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.home_bk),
                contentScale = ContentScale.FillWidth,
                colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(SurfaceDark)
                else ColorFilter.tint(SurfaceLight)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        content = content
    )
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        fontSize = 30.sp,
        modifier = Modifier.padding(top = 20.dp),
        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun Subtitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun Subtext(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(
        text = text,
        fontSize = 18.sp,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = color
    )
}

@Composable
fun Balance(getBalance: () -> Number) {
    var showBalance by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Balance: ${if (showBalance) getBalance() else "*"}",
            fontSize = 20.sp,
            fontWeight = FontWeight(500),
            textAlign = TextAlign.Start,
        )
        IconToggleButton(showBalance, { showBalance = !showBalance }) {
            VisibilityIcon(show = showBalance)
        }
    }
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(6.dp),
    shape: Shape = RectangleShape,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    modifier.padding(20.dp)
    androidx.compose.material.Button(
        onClick,
        modifier,
        enabled,
        interactionSource,
        elevation,
        shape,
        border,
        colors,
        contentPadding,
        content
    )
}

@Composable
fun Card(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10.dp),
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = BorderStroke(1.dp, strokeColor()),
    elevation: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        border = border,
        content = content
    )
}

@Composable
fun OptionsCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, strokeColor()),
        elevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp)
    ) {
        Column(
            Modifier.padding(horizontal = 20.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            content = content
        )
    }
}

@Composable
fun MenuOption(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Text(
        text = text, fontSize = 20.sp, modifier = modifier.clickable(onClick = onClick)
    )
}

@Composable
fun BottomButtonBar(
    onAccept: () -> Unit,
    acceptText: String,
    onCancel: () -> Unit,
    cancelText: String = "REGRESAR",
    isAcceptEnabled: Boolean = true
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(20.dp), horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onCancel() },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(secondaryBtnColor())
        ) {
            Text(cancelText)
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            onClick = { onAccept() },
            enabled = isAcceptEnabled,
            modifier = Modifier.weight(1f),
        ) {
            Text(acceptText)
        }
    }
}

@Composable
fun ConfirmDialog(
    title: String,
    onAccept: () -> Unit,
    onDismissRequest: () -> Unit,
    extraContent: @Composable ColumnScope.() -> Unit = {},
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card {
            Column(Modifier.padding(20.dp)) {
                Subtitle(title)

                extraContent()

                Spacer(Modifier.height(10.dp))

                Row {
                    Button(
                        onClick = { onDismissRequest() },
                        colors = ButtonDefaults.buttonColors(secondaryBtnColor()),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("CANCELAR")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { onAccept() }, modifier = Modifier.weight(1f)
                    ) {
                        Text("ACEPTAR")
                    }
                }
            }
        }
    }
}

@Composable
fun VisibilityIcon(show: Boolean) = Icon(
    painter = painterResource(if (show) R.drawable.visibility_off else R.drawable.visibility),
    contentDescription = "Balance visibility button"
)