package com.finalproject.dnd_track.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resource_options")
data class ResourceOption (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val resName: String,
    val defaultMaxUses: Int
)