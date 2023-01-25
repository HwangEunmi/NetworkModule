package com.kakaovx.practice.networkmodule.network.di

import com.kakaovx.practice.network.ConverterTypeFactory
import com.kakaovx.practice.networkmodule.network.CheckNetworkInterceptor
import com.kakaovx.practice.networkmodule.network.GithubApi
import com.kakaovx.practice.networkmodule.network.TokenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ApiModule {
    @Provides
    @Singleton
    fun provideGithubApi(
        retrofit: Retrofit
    ): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenApi(): TokenApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(ConverterTypeFactory())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addNetworkInterceptor(CheckNetworkInterceptor())
                    .build()
            ).build()

        return retrofit.create(TokenApi::class.java)
    }
}