package com.example.truffol.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

//Bottom Nav Screens
sealed class Screens(val route: String, val label: String, val icon: ImageVector) {
    object TruffleListScreen : Screens("gotoTruffleListScreen", "Truffles", Icons.Default.Restaurant)
    object ShopListScreen : Screens("gotoShopListScreen", "Shops", Icons.Default.ShoppingCart)
    object ProfileScreen : Screens("gotoProfileScreen", "Profile", Icons.Default.Face)

    //Main Activity Screens
    object BasketScreen : Screens("gotoBasketScreen", "Basket", Icons.Default.Favorite)
}

sealed class ChipScreens(val route: String) {
    object RecipesScreen : ChipScreens("gotoRecipesScreen")
    object ChipBScreen : ChipScreens("gotoChipBScreen")
    object ChipCScreen : ChipScreens("gotoChipCScreen")
    object ChipDScreen : ChipScreens("gotoChipDScreen")
}

sealed class DetailScreens(val route: String) {
    object TruffleDetailScreen : DetailScreens("gotoTruffleDetailScreen")
    object ShopDetailScreen : DetailScreens("gotoShopDetailScreen")
}