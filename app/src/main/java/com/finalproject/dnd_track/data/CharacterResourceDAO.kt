package com.finalproject.dnd_track.data

import androidx.room.*

@Dao
interface CharacterResourceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterResource: CharacterResource): Long

    @Query("UPDATE character_resource SET remUses = :remUses WHERE charId = :charId AND resOptId = :resOptId")
    suspend fun updateUses(charId: Long, resOptId: Long, remUses: Int): Int

    @Query("DELETE FROM character_resource WHERE charId = :charId AND resOptId = :resOptId")
    suspend fun delete(charId: Long, resOptId: Long): Int
}