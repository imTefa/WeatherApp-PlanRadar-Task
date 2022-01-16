package com.planradar.task.di

import android.content.Context
import com.planradar.task.utils.resourcewrapper.ResourceWrapper
import com.planradar.task.utils.resourcewrapper.ResourceWrapperImpl
import com.planradar.task.utils.systemmanger.NetworkManger
import com.planradar.task.utils.systemmanger.NetworkMangerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {

    @Provides
    fun provideResource(
        @ApplicationContext context: Context
    ): ResourceWrapper {
        return ResourceWrapperImpl(context)
    }


    @Provides
    fun provideSystemManger(
        @ApplicationContext context: Context
    ): NetworkManger = NetworkMangerImpl(context)

}