package com.a1g0.tmdbclient.data.local.dao

import androidx.room.*
import com.a1g0.tmdbclient.data.model.Movie
import com.a1g0.tmdbclient.data.model.MovieDB
import com.a1g0.tmdbclient.utils.Constants.Companion.TABLE_NAME


/**
 * Data Access Object[DAO]
 */
@Dao
interface MoviesDao {

    /**
     * Insert [Movies] into the Movies Table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(moviesList: List<MovieDB>)

    /**
     * Fetch all movies
     */
    @Query("Select * from $TABLE_NAME where category_name=:movieCategory")
    fun getAllMovies(movieCategory:String) : List<MovieDB>

    /**
     * Delete local copy of Movies
     */
    @Query("DELETE FROM $TABLE_NAME")
    suspend fun clearLocalData()


}