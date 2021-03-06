package com.example.truffol.presentation.ui.shopview.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truffol.domain.model.Shop
import com.example.truffol.interactors.shop.SearchShopsUseCase
import com.example.truffol.presentation.ui.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShopListViewModel @Inject constructor(
    private val searchShopsUseCase: SearchShopsUseCase
) : ViewModel() {

    val shopList: MutableState<List<Shop>> = mutableStateOf(ArrayList())
    val loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()

    init {
        triggerEvent(ShopListEvent.GetShopList)
    }

    private fun triggerEvent(event : ShopListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is ShopListEvent.GetShopList -> {
                        getShopListUseCase()
                    }
                }
            } catch (e: Exception) {
                Timber.e("launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Timber.d("launchJob: finally called.")
            }
        }
    }

    private fun getShopListUseCase() {
        searchShopsUseCase.run().onEach { dataState ->
            dataState.loading.let {
                Timber.d("SearchShopsUseCase: onLoading")
                loading.value = it
            }

            dataState.data?.let { list ->
                Timber.d("SearchShopsUseCase: onSuccess")
                shopList.value = list
            }

            dataState.error?.let { error ->
                Timber.e("SearchShopsUseCase onError: $error")
                dialogQueue.appendErrorMessage("An Error Occurred", error)
            }
        }.launchIn(viewModelScope)
    }

    private fun printShops(){
        for(shop in shopList.value){
            Timber.d("shop : $shop")
        }
    }

}