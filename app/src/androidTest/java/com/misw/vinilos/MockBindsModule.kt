package com.misw.vinilos

import com.misw.vinilos.data.repository.VinilosRepositoryImpl
import com.misw.vinilos.domain.VinilosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [BindsModule::class]
)
abstract class MockBindsModule {

    @Binds
    abstract fun bindRepository(
        mockVinilosRepositoryImpl: MockVinilosRepositoryImpl,
    ): VinilosRepository
}