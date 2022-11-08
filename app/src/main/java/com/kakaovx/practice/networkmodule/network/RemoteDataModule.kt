package com.kakaovx.practice.networkmodule.network

import com.kakaovx.practice.networkmodule.repository.GitRepository
import com.kakaovx.practice.networkmodule.repository.GitRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [RemoteDataModule.ApiModule::class])
internal abstract class RemoteDataModule {
    @Binds
    abstract fun bindsGitRepository(
        repository: GitRepositoryImpl
    ): GitRepository


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
    }
}