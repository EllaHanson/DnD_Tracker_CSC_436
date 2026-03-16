package com.finalproject.dnd_track.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.finalproject.dnd_track.Resource

@Composable
fun AddCardPopup(
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var resourceName by remember { mutableStateOf("") }
    var maxUses by remember { mutableStateOf("") }
    val maxUsesInt = maxUses.toIntOrNull()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Resource Cards") },
        text = {
            Column {
                OutlinedTextField(
                    value = resourceName,
                    onValueChange = { resourceName = it },
                    label = { Text("Resource Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = maxUses,
                    onValueChange = { maxUses = it },
                    label = { Text("Max Uses") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(resourceName, maxUses) },
                enabled = resourceName.isNotBlank() && maxUsesInt != null && maxUsesInt > 0 && maxUsesInt < 100
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}