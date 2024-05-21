package com.bruno13palhano.core.di

import com.bruno13palhano.core.data.local.Data
import com.bruno13palhano.core.data.local.HistoryLocalData
import com.bruno13palhano.core.model.History
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class HistoryData

@InstallIn(SingletonComponent::class)
@Module
internal abstract class LocalDataModule {

    @HistoryData
    @Singleton
    @Binds
    abstract fun bindHistoryLocalData(localData: HistoryLocalData): Data<History>
}