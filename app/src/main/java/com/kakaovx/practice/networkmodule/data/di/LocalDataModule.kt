package com.kakaovx.practice.networkmodule.data.di

import com.kakaovx.practice.networkmodule.data.datasource.local.TokenLocalDataSource
import com.kakaovx.practice.networkmodule.data.datasource.local.TokenLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Jinny (Hwang)
 *
 * Local Data 모듈
 */
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