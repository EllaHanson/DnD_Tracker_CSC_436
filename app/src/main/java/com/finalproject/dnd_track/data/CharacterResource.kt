package com.finalproject.dnd_track.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "character_resource",
    primaryKeys = ["charId", "resOptId"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntry::class,
            parentColumns = ["id"],
            childColumns = ["charId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ResourceOption::class,
            parentColumns = ["id"],
            childColumns = ["resOptId"],
            onDelete = ForeignKey.CASCADE
        )
    ]

)
data class CharacterResource (
    val charId: Long,
    val resOptId: Long,
    val remUses: Int
)