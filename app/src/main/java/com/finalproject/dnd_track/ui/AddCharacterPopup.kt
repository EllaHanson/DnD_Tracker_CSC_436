package com.finalproject.dnd_track.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.finalproject.dnd_track.Resource

@Composable
fun AddCharacterPopup(
    onConfirm: (name: String, characterClass: String, maxHp: Int, resources: List<Resource>) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var characterClass by remember { mutableStateOf("") }
    var maxHpText by remember { mutableStateOf("") }
    var abilities by remember { mutableStateOf(listOf<Pair<String, String>>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Character") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = characterClass,
                    onValueChange = { characterClass = it },
                    label = { Text("Class") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = maxHpText,
                    onValueChange = { maxHpText = it },
                    label = { Text("Max HP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Abilities", style = MaterialTheme.typography.titleSmall)

                abilities.forEachIndexed { index, (abilityName, abilityUses) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = abilityName,
                            onValueChange = { newName ->
                                abilities = abilities.toMutableList().also { it[index] = newName to abilityUses }
                            },
                            label = { Text("Name") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = abilityUses,
                            onValueChange = { newUses ->
                                abilities = abilities.toMutableList().also { it[index] = abilityName to newUses }
                            },
                            label = { Text("Uses") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.width(70.dp)
                        )
                        IconButton(onClick = {
                            abilities = abilities.toMutableList().also { it.removeAt(index) }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove ability")
                        }
                    }
                }

                Button(
                    onClick = { abilities = abilities + ("" to "") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Add Ability")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val maxHp = maxHpText.toIntOrNull() ?: 0
                val resources = abilities.mapNotNull { (abilityName, abilityUses) ->
                    val uses = abilityUses.toIntOrNull() ?: return@mapNotNull null
                    if (abilityName.isBlank()) return@mapNotNull null
                    Resource(id = 0, name = abilityName, usesLeft = uses, maxUses = uses)
                }
                if (name.isNotBlank() && characterClass.isNotBlank() && maxHp > 0) {
                    onConfirm(name, characterClass, maxHp, resources)
                }
            }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}