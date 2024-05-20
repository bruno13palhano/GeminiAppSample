package com.bruno13palhano.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IOScope

@InstallIn(SingletonComponent::class)
@Module
object CoroutineScopeModule {

    @Singleton
    @ApplicationScope
    @Provides
    fun providesDefaultCoroutineScope(
        @Dispatcher(AppDispatcher.DEFAULT) dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Singleton
    @IOScope
    @Provides
    fun providesIOCoroutineScope(
        @Dispatcher(AppDispatcher.IO) dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}