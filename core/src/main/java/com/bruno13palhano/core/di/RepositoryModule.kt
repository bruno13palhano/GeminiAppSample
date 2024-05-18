package com.bruno13palhano.core.di

import com.bruno13palhano.core.data.repository.GenerativeModelRepository
import com.bruno13palhano.core.data.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class GenerativeModelRep

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {

    @GenerativeModelRep
    @Singleton
    @Binds
    abstract fun bindGenerativeModelRepository(repository: GenerativeModelRepository): Repository
}