package com.codelabs.cryptoapp.presentation.coinlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.cryptoapp.common.Resource
import com.codelabs.cryptoapp.domain.model.Coin
import com.codelabs.cryptoapp.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CoinListState>()
    val state: LiveData<CoinListState> = _state

    fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Loading ->
                    _state.value = CoinListState.Loading
                is Resource.Error ->
                    _state.value = CoinListState.Error(message = result.message ?: "Error")
                is Resource.Success ->
                    _state.value = CoinListState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

}