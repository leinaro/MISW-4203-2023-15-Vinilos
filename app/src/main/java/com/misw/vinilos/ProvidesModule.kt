package com.misw.vinilos

import android.content.Context
import androidx.room.Room
import com.misw.vinilos.data.datasource.api.AlbumsApi
import com.misw.vinilos.data.datasource.api.MusicianApi
import com.misw.vinilos.data.datasource.local.AlbumDao
import com.misw.vinilos.data.datasource.local.MusicianDao
import com.misw.vinilos.data.datasource.local.VinilosDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "http://ec2-3-12-155-51.us-east-2.compute.amazonaws.com:3000/"

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {
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
    @Singleton
    fun providesVinilosDatabase(
        @ApplicationContext appContext: Context,
    ) : VinilosDatabase {
        return Room.databaseBuilder(
            appContext,
            VinilosDatabase::class.java,
            name = "vinilos-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesMusicianDao(
        vinilosDatabase: VinilosDatabase,
    ) : MusicianDao {
        return vinilosDatabase.musicianDao()
    }


    @Provides
    fun providesAlbumDao(
        vinilosDatabase: VinilosDatabase,
    ) : AlbumDao {
        return vinilosDatabase.albumDao()
    }
}

