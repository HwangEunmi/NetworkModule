package com.kakaovx.practice.networkmodule.data.di

import com.kakaovx.practice.network.ConverterTypeFactory
import com.kakaovx.practice.networkmodule.data.network.util_retrofit.AddHeaderInterceptor
import com.kakaovx.practice.networkmodule.data.network.util_retrofit.TokenAuthenticator
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
 * Network 관련 모듈
 */
@InstallIn(SingletonComponent::class)
@Module
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenAuthenticator(): TokenAuthenticator =
        TokenAuthenticator(RepositoryModule.ProvideModule.provideTokenRepository())

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS) // default value : 10
            .readTimeout(20, TimeUnit.SECONDS) // default value : 10
            .writeTimeout(20, TimeUnit.SECONDS) // default value : 10
            .authenticator(provideTokenAuthenticator())
            .addNetworkInterceptor(AddHeaderInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(ConverterTypeFactory())
            .client(provideOkHttpClient())
            .build()
}