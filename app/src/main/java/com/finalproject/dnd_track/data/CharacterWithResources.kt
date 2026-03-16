package com.finalproject.dnd_track.data

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithResources(
    @Embedded val character: CharacterEntry,
    @Relation(
        entity = CharacterResource::class,
        parentColumn = "id",
        entityColumn = "charId"
    )
    val resources: List<CharacterResourceWithOption>
)