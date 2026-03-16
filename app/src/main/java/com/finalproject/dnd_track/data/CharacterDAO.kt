package com.finalproject.dnd_track.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDAO {
    @Transaction
    @Query("SELECT * FROM characters ORDER BY name")
    fun getAllWithResources(): Flow<List<CharacterWithResources>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntry): Long

    @Query("UPDATE characters SET currHp = :newHp WHERE id = :charId")
    suspend fun updateHp(charId: Long, newHp: Int): Int

    @Query("UPDATE characters SET name = :name, charClass = :characterClass, maxHp = :maxHp, currHp = MIN(currHp, :maxHp) WHERE id = :charId")
    suspend fun updateCharacter(charId: Long, name: String, characterClass: String, maxHp: Int)

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCount(): Int
}