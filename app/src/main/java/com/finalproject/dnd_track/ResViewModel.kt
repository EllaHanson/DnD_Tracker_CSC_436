package com.finalproject.dnd_track

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.dnd_track.data.DndDatabase
import com.finalproject.dnd_track.data.DndRepository
import kotlinx.coroutines.launch

class ResViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DndRepository(DndDatabase.getDatabase(application))

    val characters = mutableStateListOf<Character>()

    init {
        viewModelScope.launch {
            repository.prepopulateIfEmpty()
            repository.characters.collect { dbCharacters ->
                characters.clear()
                characters.addAll(dbCharacters)
            }
        }
    }

    fun addCharacter(name: String, characterClass: String, maxHp: Int, resources: List<Resource>) {
        viewModelScope.launch {
            repository.addCharacter(name, characterClass, maxHp, resources)
        }
    }

    fun updateCharacter(charId: Int, name: String, characterClass: String, maxHp: Int) {
        viewModelScope.launch {
            repository.updateCharacter(charId, name, characterClass, maxHp)
        }
    }

    fun changeCharHp(charId: Int, deltaHp: Int, isHealing: Boolean) {
        viewModelScope.launch {
            val character = characters.find { it.id == charId } ?: return@launch
            val newHp = if (isHealing) {
                (character.currHp + deltaHp).coerceAtMost(character.maxHp)
            } else {
                (character.currHp - deltaHp).coerceAtLeast(0)
            }
            repository.updateCharacterHp(charId, newHp)
        }
    }

    fun changeResUsesLeft(charId: Int, resOptId: Long, newUsesLeft: Int) {
        viewModelScope.launch {
            repository.updateResourceUses(charId, resOptId, newUsesLeft)
        }
    }

    fun addResourceToCharacter(charId: Int, resource: Resource) {
        viewModelScope.launch {
            repository.addResourceToCharacter(charId, resource)
        }
    }

    fun deleteResourceFromCharacter(charId: Int, resOptId: Long) {
        viewModelScope.launch {
            repository.deleteResourceFromCharacter(charId, resOptId)
        }
    }


}