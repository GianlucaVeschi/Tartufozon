package com.example.truffol.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.truffol.presentation.ui.Screens
import com.example.truffol.presentation.ui.profileview.ProfileScreen
import com.example.truffol.presentation.ui.shopview.list.ShopListScreen
import com.example.truffol.presentation.ui.shopview.list.ShopListViewModel
import com.example.truffol.presentation.ui.truffleview.list.TruffleListScreen
import com.example.truffol.presentation.ui.truffleview.list.TruffleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.truffol.presentation.ui.basket.BasketScreen
import com.example.truffol.presentation.ui.shopview.detail.ShopDetailViewModel
import com.example.truffol.presentation.ui.truffleview.detail.TruffleDetailViewModel
import com.example.truffol.presentation.util.CustomConnectivityManager
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: CustomConnectivityManager

    private val shopListViewModel: ShopListViewModel by viewModels()
    private val truffleListViewModel: TruffleListViewModel by viewModels()

    private val truffleDetailViewModel : TruffleDetailViewModel by viewModels()
    private val shopDetailViewModel : ShopDetailViewModel by viewModels()


    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            val isNetworkAvailable = connectivityManager.isNetworkAvailable.value
            Timber.d("onCreate: IS NETWORK AVAILABLE? $isNetworkAvailable")

            Surface(color = MaterialTheme.colors.background) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Truffol Ecommerce", color = Color.Black)
                            },
                            actions = {
                                IconButton(
                                    onClick = {
                                        navHostController.apply { popBackStack(graph.startDestinationId, false)}
                                        navHostController.navigate(Screens.BasketScreen.route)
                                    }) {
                                    Icon(
                                        Icons.Default.Favorite,
                                        contentDescription = "Favorite Icon"
                                    )
                                }
                            },
                            backgroundColor = Color.White
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navHostController = navHostController)
                    }
                ) {
                    // Apply the padding globally to the whole BottomNavScreensController
                    Box(modifier = Modifier.padding(it)) {
                        BottomNavScreensController(navHostController = navHostController)
                    }
                }
            }
        }
    }

    @Composable
    fun BottomNavBar(navHostController: NavHostController) {

        val bottomNavScreens = listOf(
            Screens.ShopListScreen,
            Screens.TruffleListScreen,
            Screens.ProfileScreen
        )

        BottomNavigation(
            backgroundColor = Color.White
        ) {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            bottomNavScreens.forEach {
                BottomNavigationItem(
                    icon = { Icon(it.icon, contentDescription = null) },
                    selected = currentRoute == it.route,
                    label = { Text(text = it.label) },
                    onClick = {
                        navHostController.popBackStack(
                            navHostController.graph.startDestinationId, false
                        )
                        if (currentRoute != it.route) {
                            navHostController.navigate(it.route)
                        }
                    })
            }
        }
    }

    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    @Composable
    fun BottomNavScreensController(navHostController: NavHostController) {
        NavHost(
            navController = navHostController,
            startDestination = Screens.TruffleListScreen.route
        ) {
            composable(Screens.ShopListScreen.route) {
                ShopListScreen(
                    shopListViewModel = shopListViewModel,
                    shopDetailViewModel = shopDetailViewModel,
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value
                )
            }

            composable(Screens.TruffleListScreen.route) {
                TruffleListScreen(
                    truffleListViewModel = truffleListViewModel,
                    truffleDetailViewModel = truffleDetailViewModel,
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                )
            }

            composable(Screens.ProfileScreen.route) {
                ProfileScreen()
            }

            //This shouldn't be here, as it is not part of the bottom navigation
            composable(Screens.BasketScreen.route) {
                BasketScreen()
            }
        }
    }
}