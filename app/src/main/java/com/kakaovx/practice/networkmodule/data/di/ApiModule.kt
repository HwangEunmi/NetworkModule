package com.kakaovx.practice.networkmodule.data.di

import com.kakaovx.practice.network.ConverterTypeFactory
import com.kakaovx.practice.networkmodule.data.api.GithubApi
import com.kakaovx.practice.networkmodule.data.api.TokenApi
import com.kakaovx.practice.networkmodule.data.network.util_retrofit.CheckNetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Jinny (Hwang)
 *
 * Api 모듈
 */
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