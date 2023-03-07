package com.example.composeuistate.app.di

import android.content.Context
import androidx.room.Room
import com.example.composeuistate.data.local.db.MovieDao
import com.example.composeuistate.data.local.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): MovieDatabase = Room.databaseBuilder(context, MovieDatabase::class.java, "movie-db").build()

    @Provides
    fun providesAuthorDao(
        database: MovieDatabase,
    ): MovieDao = database.movieDao()
}