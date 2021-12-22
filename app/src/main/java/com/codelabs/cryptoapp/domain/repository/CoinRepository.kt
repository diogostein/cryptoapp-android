package com.codelabs.cryptoapp.domain.repository

import com.codelabs.cryptoapp.data.remote.dto.CoinDetailDto
import com.codelabs.cryptoapp.data.remote.dto.CoinDto

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetailDto
}