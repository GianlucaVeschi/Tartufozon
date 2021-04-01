package com.example.tartufozon.presentation.components.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tartufozon.presentation.components.CircularIndeterminateProgressBar
import com.example.tartufozon.presentation.components.ConnectivityMonitor
import com.example.tartufozon.presentation.components.GenericDialog
import com.example.tartufozon.presentation.components.GenericDialogInfo
import java.util.*

private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2,
)

@ExperimentalMaterialApi
@Composable
fun AppTheme(
    isNetworkAvailable: Boolean,
    displayProgressBar: Boolean,
    scaffoldState: ScaffoldState?,
    dialogQueue: Queue<GenericDialogInfo>? = null,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = LightThemeColors,
        shapes = AppShapes
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Grey1)
        ){
            Column{
                ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                content()
            }
            CircularIndeterminateProgressBar(isDisplayed = displayProgressBar, 0.5f)
            ProcessDialogQueue(
                dialogQueue = dialogQueue,
            )
        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>?,
) {
    dialogQueue?.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}
