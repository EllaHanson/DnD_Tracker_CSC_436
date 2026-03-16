package com.finalproject.dnd_track.data

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterResourceWithOption(
    @Embedded val characterResource: CharacterResource,
    @Relation(
        parentColumn = "resOptId",
        entityColumn = "id"
    )
    val resourceOption: ResourceOption
)