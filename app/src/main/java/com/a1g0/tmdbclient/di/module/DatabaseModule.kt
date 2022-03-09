package com.a1g0.tmdbclient.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.a1g0.tmdbclient.data.local.MoviesDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) = MoviesDatabase.getInstance(application)

    @Provides
    @Singleton
    fun provideBookmarkDao(database: MoviesDatabase) = database.getMoviesDao()

}