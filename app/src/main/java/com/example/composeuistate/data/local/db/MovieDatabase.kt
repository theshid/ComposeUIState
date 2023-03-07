package com.example.composeuistate.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composeuistate.data.local.model.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}