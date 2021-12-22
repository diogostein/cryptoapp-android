package com.codelabs.cryptoapp.di

import com.codelabs.cryptoapp.data.remote.CoinPaprikaApi
import com.codelabs.cryptoapp.data.repository.CoinRepositoryImpl
import com.codelabs.cryptoapp.domain.repository.CoinRepository
import com.codelabs.cryptoapp.domain.usecase.GetCoinUseCase
import com.codelabs.cryptoapp.domain.usecase.GetCoinsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Provides
    @Singleton
    fun providePaprikaApi(okHttpClient: OkHttpClient): CoinPaprikaApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.coinpaprika.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }
}