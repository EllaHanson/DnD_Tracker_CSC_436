package com.finalproject.dnd_track.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntry (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val name: String,

    val charClass: String,
    val charLevel: Int,

    val currHp: Int,
    val maxHp: Int
)