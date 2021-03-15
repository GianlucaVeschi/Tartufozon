package com.example.tartufozon.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.tartufozon.presentation.ui.Screens
import com.example.tartufozon.presentation.ui.profileview.ProfileScreen
import com.example.tartufozon.presentation.ui.shopview.list.ShopListScreen
import com.example.tartufozon.presentation.ui.shopview.list.ShopListViewModel
import com.example.tartufozon.presentation.ui.truffleview.list.TruffleListScreen
import com.example.tartufozon.presentation.ui.truffleview.list.TruffleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tartufozon.presentation.components.GenericDialog
import com.example.tartufozon.presentation.components.NegativeAction
import com.example.tartufozon.presentation.components.PositiveAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            BuildScaffold(navController = navController)
            ShowDialog()
        }
    }

    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    @Composable
    fun BuildScaffold(navController: NavHostController) {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "tartufozon") },
                        actions = {
                            IconButton(onClick = { Timber.d("Mail clicked") }) {
                                Icon(Icons.Default.Email, contentDescription = null)
                            }
                        })
                },
                bottomBar = {
                    BottomNavBar(navController = navController)
                }
            ) {
                // Apply the padding globally to the whole BottomNavScreensController
                Box(modifier = Modifier.padding(it)) {
                    BottomNavScreensController(navController = navController)
                }
            }
        }
    }

    @Composable
    fun BottomNavBar(navController: NavHostController) {
        val bottomNavScreens = listOf(
            Screens.TruffleListScreen,
            Screens.ShopListScreen,
            Screens.ProfileScreen
        )
        BottomNavigation(
            backgroundColor = Color(0, 69, 89)
        ) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

            bottomNavScreens.forEach {
                BottomNavigationItem(
                    icon = { Icon(it.icon, contentDescription = null) },
                    selected = currentRoute == it.route,
                    label = { Text(text = it.label) },
                    onClick = {
                        navController.popBackStack(
                            navController.graph.startDestination, false
                        )
                        if (currentRoute != it.route) {
                            navController.navigate(it.route)
                        }
                    })
            }
        }
    }

    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    @Composable
    fun BottomNavScreensController(
        navController: NavHostController
    ) {
        NavHost(navController = navController, startDestination = Screens.ShopListScreen.route) {

            composable(Screens.TruffleListScreen.route) {
                val viewModel: TruffleListViewModel by viewModels()
                TruffleListScreen(viewModel)
            }

            composable(Screens.ShopListScreen.route) {
                val factory = HiltViewModelFactory(LocalContext.current, it)
                val viewModel: ShopListViewModel = viewModel("ShopListViewModel", factory)
                ShopListScreen(viewModel)
            }

            composable(Screens.ProfileScreen.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun ShowDialog() {
    val isShowing = remember { mutableStateOf(true) }
    if (isShowing.value) {
        GenericDialog(
            onDismiss = { isShowing.value = false },
            title = "Generic Dialog",
            description = "Hey look a dialog descrip",
            positiveAction = PositiveAction(
                positiveBtnTxt = "OK",
                onPositiveAction = { isShowing.value = false }
            ),
            negativeAction = NegativeAction(
                negativeBtnTxt = "Cancel",
                onNegativeAction = { isShowing.value = false }
            )
        )
    }
}