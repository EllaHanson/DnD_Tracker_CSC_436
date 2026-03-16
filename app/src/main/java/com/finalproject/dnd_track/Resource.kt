package com.finalproject.dnd_track

data class Resource (
    val id: Long,
    val name: String,
    val usesLeft: Int,
    val maxUses: Int
)