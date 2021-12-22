package com.codelabs.cryptoapp.presentation.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.cryptoapp.common.Resource
import com.codelabs.cryptoapp.domain.usecase.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    val getCoinUseCase: GetCoinUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CoinDetailState>()
    val state: LiveData<CoinDetailState> = _state

    fun getCoinDetail(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Loading ->
                    _state.value = CoinDetailState.Loading
                is Resource.Error ->
                    _state.value = CoinDetailState.Error(message = result.message ?: "Error")
                is Resource.Success ->
                    _state.value = CoinDetailState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }
}