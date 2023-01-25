package com.kakaovx.practice.networkmodule.network.di

import com.kakaovx.practice.networkmodule.repository.GitRepository
import com.kakaovx.practice.networkmodule.repository.GitRepositoryImpl
import com.kakaovx.practice.networkmodule.repository.TokenRepository
import com.kakaovx.practice.networkmodule.repository.TokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindsGitRepository(
        repository: GitRepositoryImpl
    ): GitRepository

    @InstallIn(SingletonComponent::class)
    @Module
    internal object ProvideModule {
        @Provides
        @Singleton
        fun provideTokenRepository(): TokenRepository =
            TokenRepositoryImpl(
                localDataSource = LocalDataModule.ProvideModule.provideTokenLocalDataSource(),
                remoteDataSource = RemoteDataModule.ProvideModule.provideTokenRemoteDataSource()
            )
    }
}