package com.a1g0.tmdbclient.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.a1g0.tmdbclient.data.local.dao.MoviesDao
import com.a1g0.tmdbclient.data.model.MovieDB

@Database(
    entities = [MovieDB::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getMoviesDao() : MoviesDao

    companion object {

        const val DATABASE_NAME = "MOVIES_DATABASE"

        @Volatile
        private var INSTANCE : MoviesDatabase? = null

        fun getInstance(context : Context) : MoviesDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }

    }

}