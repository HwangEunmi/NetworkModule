package com.kakaovx.practice.networkmodule.data.di

import com.kakaovx.practice.networkmodule.data.datasource.remote.GitRemoteDataSource
import com.kakaovx.practice.networkmodule.data.datasource.remote.GitRemoteDataSourceImpl
import com.kakaovx.practice.networkmodule.data.datasource.remote.TokenRemoteDataSource
import com.kakaovx.practice.networkmodule.data.datasource.remote.TokenRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Jinny (Hwang)
 *
 * Remote Data 모듈
 */
@InstallIn(SingletonComponent::class)
@Module(includes = [RemoteDataModule.ProvideModule::class])
internal abstract class RemoteDataModule {
    @Binds
    abstract fun bindsGitRemoteDataSource(
        dataSource: GitRemoteDataSourceImpl
    ): GitRemoteDataSource

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