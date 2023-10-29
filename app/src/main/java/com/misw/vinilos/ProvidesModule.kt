package com.misw.vinilos

import com.misw.vinilos.data.datasource.api.MusicianApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "http://35.203.136.6:3000/"
@Module
@InstallIn(SingletonComponent::class)
class ProvidesModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    @Provides
    @Singleton
    fun providesAlbumsApi(
        retrofit: Retrofit,
    ): AlbumsApi = retrofit.create(AlbumsApi::class.java)

    @Provides
    @Singleton
    fun providesMusicianApi(
        retrofit: Retrofit,
    ): MusicianApi = retrofit.create(MusicianApi::class.java)


    @Provides
    fun providesRepository(
        albumsApi: AlbumsApi,
        musicianApi: MusicianApi
    ): VinilosRepository = VinilosRepositoryImpl(
        albumsApi = albumsApi,
        musicianApi = musicianApi

    )

}