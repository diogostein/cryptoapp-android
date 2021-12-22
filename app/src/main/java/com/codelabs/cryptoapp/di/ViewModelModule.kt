package com.codelabs.cryptoapp.di

import com.codelabs.cryptoapp.data.remote.CoinPaprikaApi
import com.codelabs.cryptoapp.data.repository.CoinRepositoryImpl
import com.codelabs.cryptoapp.domain.repository.CoinRepository
import com.codelabs.cryptoapp.domain.usecase.GetCoinUseCase
import com.codelabs.cryptoapp.domain.usecase.GetCoinsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideCoinRepository(api: CoinPaprikaApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCoinsUseCase(repository: CoinRepository): GetCoinsUseCase {
        return GetCoinsUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCoinUseCase(repository: CoinRepository): GetCoinUseCase {
        return GetCoinUseCase(repository)
    }

}