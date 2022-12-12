package com.kakaovx.practice.networkmodule.network

import com.kakaovx.practice.network.ConverterTypeFactory
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
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS) // default value : 10
            .readTimeout(20, TimeUnit.SECONDS) // default value : 10
            .writeTimeout(20, TimeUnit.SECONDS) // default value : 10
            .addInterceptor(RequestInterceptor())
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