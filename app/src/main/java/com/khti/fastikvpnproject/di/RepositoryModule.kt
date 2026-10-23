package com.khti.fastikvpnproject.di

import com.khti.fastikvpnproject.data.repository.IpRepositoryImpl
import com.khti.fastikvpnproject.data.repository.SettingsRepositoryImpl
import com.khti.fastikvpnproject.domain.repository.IpRepository
import com.khti.fastikvpnproject.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindIpRepository(
        impl: IpRepositoryImpl
    ): IpRepository
}