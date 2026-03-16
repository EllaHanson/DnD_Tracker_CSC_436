package com.finalproject.dnd_track.ui

import android.R.attr.text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.finalproject.dnd_track.Character

@Composable
fun EditCharacterPopup(
    character: Character,
    onConfirm: (String, String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(character.name) }
    var charClass by remember { mutableStateOf(character.characterClass) }
    var maxHpText by remember { mutableStateOf(character.maxHp.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit ${character.name}") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = charClass,
                    onValueChange = { charClass = it },
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
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val maxHp = maxHpText.toIntOrNull() ?: 0
                if (name.isNotBlank() && charClass.isNotBlank() && maxHp > 0) {
                    onConfirm(name, charClass, maxHp)
                }
            }) {
                Text("Done")
            }
        }
    )
}