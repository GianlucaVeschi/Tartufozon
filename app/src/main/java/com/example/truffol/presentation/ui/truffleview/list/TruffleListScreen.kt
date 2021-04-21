package com.example.truffol.presentation.ui.truffleview.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.truffol.domain.model.Truffle
import com.example.truffol.presentation.components.shimmer.LoadingListShimmer
import com.example.truffol.presentation.ui.DetailScreens
import com.example.truffol.presentation.ui.Screens
import com.example.truffol.presentation.ui.truffleview.detail.TruffleDetailScreen
import com.example.truffol.presentation.ui.truffleview.detail.TruffleDetailViewModel
import com.example.truffol.util.Constants.TRUFFLE_KEY
import androidx.hilt.navigation.HiltViewModelFactory
import com.example.truffol.presentation.components.*
import com.example.truffol.presentation.components.theme.AppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@Composable
fun TruffleListScreen(
    truffleListViewModel: TruffleListViewModel,
    isNetworkAvailable : Boolean
) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController, startDestination = Screens.TruffleListScreen.route) {
        composable(Screens.TruffleListScreen.route) {
            TruffleListScreenContent(truffleListViewModel, navController, isNetworkAvailable)
        }
        composable(DetailScreens.TruffleDetailScreen.route) {

            val factory = HiltViewModelFactory(LocalContext.current, it)
            val truffleDetailViewModel: TruffleDetailViewModel =
                viewModel("RecipeDetailViewModel", factory)

            TruffleDetailScreen(
                navController = navController,
                truffleDetailViewModel = truffleDetailViewModel
            )
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
private fun TruffleListScreenContent(
    truffleListViewModel: TruffleListViewModel,
    navController: NavController,
    isNetworkAvailable: Boolean
) {
    val trufflesList = truffleListViewModel.trufflesList.value
    val query: String = truffleListViewModel.query.value
    val selectedCategory = truffleListViewModel.selectedCategory.value
    val loading = truffleListViewModel.loading.value
    val scrollState = rememberScrollState()
    val dialogQueue = truffleListViewModel.dialogQueue
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
        dialogQueue = dialogQueue.queue.value,
        isNetworkAvailable = isNetworkAvailable
    ) {
        Scaffold(
            topBar = {
                BuildSearchBar(
                    truffleListViewModel,
                    query,
                    selectedCategory
                )
            },
            drawerContent = { BuildDrawerContent() }
        ) {
            BuildTrufflesList(truffles = trufflesList, loading, navController)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun BuildSearchBar(
    truffleListViewModel: TruffleListViewModel,
    query: String,
    selectedCategory: TruffleCategory?
) {
    SearchAppBar(
        query = query,
        onQueryChanged = truffleListViewModel::onQueryChanged,
        onExecuteSearch = {
            truffleListViewModel.onTriggerEvent(TruffleListEvent.GetShuffledTruffleList)
        },
        categories = getAllTruffleCategories(),
        selectedCategory = selectedCategory,
        onSelectedCategoryChanged = truffleListViewModel::onSelectedCategoryChanged
    )
}

@ExperimentalCoroutinesApi
@Composable
fun BuildTrufflesList(truffles: List<Truffle>, isLoading: Boolean, navController: NavController) {
    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.surface)
    ) {
        if (isLoading) {
            LoadingListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn {
                itemsIndexed(
                    items = truffles
                ) { index, truffle ->
                    TruffleCard(truffle, onClick = {
                        navController.currentBackStackEntry?.arguments?.putInt(
                            TRUFFLE_KEY,
                            truffle.id
                        )
                        navController.navigate(
                            DetailScreens.TruffleDetailScreen.route
                        )
                    })
                }
            }
        }
    }
}