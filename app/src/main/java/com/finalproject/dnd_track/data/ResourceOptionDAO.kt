package com.finalproject.dnd_track.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourceOptionDAO {
    @Query("SELECT * FROM resource_options ORDER BY resName")
    fun getAll(): Flow<List<ResourceOption>>

    @Query("SELECT * FROM resource_options WHERE resName = :name LIMIT 1")
    suspend fun getByName(name: String): ResourceOption?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(option: ResourceOption): Long
}