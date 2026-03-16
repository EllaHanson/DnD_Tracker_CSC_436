package com.finalproject.dnd_track

data class Character(
    val id: Int,
    val name: String,
    val characterClass: String,
    val resources: List<Resource>,
    val currHp: Int,
    val maxHp: Int
)