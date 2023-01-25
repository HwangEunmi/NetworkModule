package com.kakaovx.practice.networkmodule.network.di

import com.kakaovx.practice.networkmodule.datasource.local.TokenLocalDataSource
import com.kakaovx.practice.networkmodule.datasource.local.TokenLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [LocalDataModule.ProvideModule::class])
internal abstract class LocalDataModule {

    @InstallIn(SingletonComponent::class)
    @Module
    internal object ProvideModule {
        @Provides
        @Singleton
        fun provideTokenLocalDataSource(): TokenLocalDataSource =
            TokenLocalDataSourceImpl()
    }
}