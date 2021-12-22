package com.codelabs.cryptoapp.presentation.coinlist

import com.codelabs.cryptoapp.domain.model.Coin

sealed class CoinListState {
    object Loading : CoinListState()
    class Error(val message: String) : CoinListState()
    class Success(val coins: List<Coin>) : CoinListState()
}