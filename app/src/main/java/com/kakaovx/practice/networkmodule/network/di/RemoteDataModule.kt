package com.kakaovx.practice.networkmodule.network.di

import com.kakaovx.practice.networkmodule.datasource.remote.GitDataSource
import com.kakaovx.practice.networkmodule.datasource.remote.GitDataSourceImpl
import com.kakaovx.practice.networkmodule.datasource.remote.TokenRemoteDataSource
import com.kakaovx.practice.networkmodule.datasource.remote.TokenRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [RemoteDataModule.ProvideModule::class])
internal abstract class RemoteDataModule {
    @Binds
    abstract fun bindsGitRemoteDataSource(
        dataSource: GitDataSourceImpl
    ): GitDataSource

    @InstallIn(SingletonComponent::class)
    @Module
    internal object ProvideModule {
        @Provides
        @Singleton
        fun provideTokenRemoteDataSource(): TokenRemoteDataSource =
            TokenRemoteDataSourceImpl(
                ApiModule.provideTokenApi()
            )
    }
}