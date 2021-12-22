package com.codelabs.cryptoapp.presentation.coindetail

import com.codelabs.cryptoapp.domain.model.CoinDetail

sealed class CoinDetailState {
    object Loading : CoinDetailState()
    class Error(val message: String) : CoinDetailState()
    class Success(val coinDetail: CoinDetail) : CoinDetailState()
}