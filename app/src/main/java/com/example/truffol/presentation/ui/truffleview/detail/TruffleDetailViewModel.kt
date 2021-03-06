package com.example.truffol.presentation.ui.truffleview.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truffol.domain.model.Truffle
import com.example.truffol.interactors.truffle.GetTruffleUseCase
import com.example.truffol.presentation.ui.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

const val STATE_KEY_TRUFFLE = "truffle.state.truffle.key"

@HiltViewModel
class TruffleDetailViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val getTruffleUseCase: GetTruffleUseCase
) : ViewModel() {

    val truffle: MutableState<Truffle?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()

    init {
        // restore if process dies
        state.get<Int>(STATE_KEY_TRUFFLE)?.let { truffleId ->
            onTriggerEvent(TruffleDetailEvent.GetTruffleDetailEvent(truffleId))
        }
    }

    fun onTriggerEvent(detailEvent: TruffleDetailEvent) {
        try {
            when (detailEvent) {
                //UseCase #1
                is TruffleDetailEvent.GetTruffleDetailEvent -> {
                    getTruffleDetailUseCase(detailEvent.id)
                }
            }
        } catch (e: Exception) {
            Timber.e("launchJob: Exception: ${e}, ${e.cause}")
            e.printStackTrace()
        }
    }

    private fun getTruffleDetailUseCase(truffleId: Int) {
        loading.value = true

        getTruffleUseCase.run(truffleId).onEach { dataState ->
            dataState.loading.let {
                loading.value = dataState.loading
                Timber.d("onLoading...")
            }
            dataState.data?.let {
                Timber.d("onSuccess ${it.tartufoName}")
                truffle.value = it
            }
            dataState.error?.let { error ->
                Timber.d("GetTruffleUseCase onError $error")
                dialogQueue.appendErrorMessage("An error appeared", error)
            }
        }.launchIn(viewModelScope)

        loading.value = false
    }

}