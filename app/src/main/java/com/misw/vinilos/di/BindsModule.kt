package com.misw.vinilos.di

import com.misw.vinilos.data.repository.NetworkConnectivityService
import com.misw.vinilos.data.repository.NetworkConnectivityServiceImpl
import com.misw.vinilos.data.repository.VinilosRepositoryImpl
import com.misw.vinilos.domain.VinilosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun bindRepository(
        vinilosRepositoryImpl: VinilosRepositoryImpl,
    ): VinilosRepository

    @Binds
    abstract fun bindNetworkConnectivityService(
        networkConnectivityServiceImpl: NetworkConnectivityServiceImpl,
    ): NetworkConnectivityService
}