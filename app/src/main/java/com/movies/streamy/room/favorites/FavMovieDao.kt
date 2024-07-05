package com.movies.streamy.room.favorites

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteItem: FavMovieEntity)

    @Query("SELECT * FROM favorites")
    fun getAllMovieFavorites(): LiveData<List<FavMovieEntity>>

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)



    @Query("SELECT * FROM favorites WHERE id = :id LIMIT 1")
    suspend fun isFavorite(id: Int): FavMovieEntity?
}