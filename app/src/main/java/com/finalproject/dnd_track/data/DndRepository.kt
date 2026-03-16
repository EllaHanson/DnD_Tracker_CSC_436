package com.finalproject.dnd_track.data

import com.finalproject.dnd_track.Character
import com.finalproject.dnd_track.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DndRepository(private val db: DndDatabase) {

    val characters: Flow<List<Character>> = db.characterDAO().getAllWithResources()
        .map { list -> list.map { it.toDomain() } }

    suspend fun prepopulateIfEmpty() {
        if (db.characterDAO().getCount() == 0) {
            addCharacter("Thorin", "Barbarian", 18, listOf(Resource(0, "Rage", 2, 2)))
            addCharacter(
                "Elara", "Druid", 12, listOf(
                    Resource(0,"Wildshape", 3, 3),
                    Resource(0, "Channel Divinity", 1, 2)
                )
            )
        }
    }

    suspend fun addCharacter(name: String, characterClass: String, maxHp: Int, resources: List<Resource>) {
        val charId = db.characterDAO().insert(
            CharacterEntry(name = name, charClass = characterClass, charLevel = 1, currHp = maxHp, maxHp = maxHp)
        )
        resources.forEach { resource ->
            val resOptId = db.resourceOptionDAO().insert(
                ResourceOption(
                    resName = resource.name,
                    defaultMaxUses = resource.maxUses
                )
            )

            db.characterResourceDAO().insert(
                CharacterResource(
                    charId = charId,
                    resOptId = resOptId,
                    remUses = resource.usesLeft
                )
            )
        }
    }

    suspend fun updateCharacterHp(charId: Int, newHp: Int) {
        db.characterDAO().updateHp(charId.toLong(), newHp)
    }

    suspend fun updateCharacter(charId: Int, name: String, characterClass: String, maxHp: Int) {
        db.characterDAO().updateCharacter(charId.toLong(), name, characterClass, maxHp)
    }

    suspend fun updateResourceUses(charId: Int, resOptId: Long, newUses: Int) {
        db.characterResourceDAO().updateUses(charId.toLong(), resOptId, newUses)
    }

    private fun CharacterWithResources.toDomain(): Character {
        return Character(
            id = character.id.toInt(),
            name = character.name,
            characterClass = character.charClass,
            resources = resources.map {
                Resource(
                    id = it.resourceOption.id,
                    name = it.resourceOption.resName,
                    usesLeft = it.characterResource.remUses,
                    maxUses = it.resourceOption.defaultMaxUses
                )
            },
            currHp = character.currHp,
            maxHp = character.maxHp
        )
    }

    suspend fun addResourceToCharacter(charId: Int, resource: Resource) {
        val resourceOptId = db.resourceOptionDAO().insert(
            ResourceOption(
                resName = resource.name,
                defaultMaxUses = resource.maxUses
            )
        )

        db.characterResourceDAO().insert(
            CharacterResource(
                charId = charId.toLong(),
                resOptId = resourceOptId,
                remUses = resource.usesLeft
            )
        )
    }

    suspend fun deleteResourceFromCharacter(charId: Int, resOptId: Long) {
        db.characterResourceDAO().delete(charId.toLong(), resOptId)
    }



}