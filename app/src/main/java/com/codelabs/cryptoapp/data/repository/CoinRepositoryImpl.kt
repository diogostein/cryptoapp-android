package com.codelabs.cryptoapp.data.repository

import com.codelabs.cryptoapp.data.remote.CoinPaprikaApi
import com.codelabs.cryptoapp.data.remote.dto.CoinDetailDto
import com.codelabs.cryptoapp.data.remote.dto.CoinDto
import com.codelabs.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl(
    private val api: CoinPaprikaApi
) : CoinRepository {

    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }
}