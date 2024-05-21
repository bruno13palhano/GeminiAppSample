package com.bruno13palhano.core.di

import android.content.Context
import cache.HistoryTableQueries
import com.bruno13palhano.cache.AppDatabase
import com.bruno13palhano.core.data.database.DatabaseFactory
import com.bruno13palhano.core.data.database.DriverFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabaseFactoryDriver(@ApplicationContext context: Context): AppDatabase {
        return DatabaseFactory(
            driverFactory = DriverFactory(context = context)
        ).createDriver()
    }

    @Provides
    @Singleton
    fun provideHistoryQueries(database: AppDatabase): HistoryTableQueries =
        database.historyTableQueries
}