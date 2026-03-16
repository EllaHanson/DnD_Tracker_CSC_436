package com.finalproject.dnd_track.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finalproject.dnd_track.ResViewModel
import com.finalproject.dnd_track.Resource
import kotlin.collections.forEach

@Composable
fun ResourceCards(
    characterId: Int,
    resources: List<Resource>,
    modifier: Modifier,
    model: ResViewModel
) {
    Column() {
        resources.forEach { resource ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    var showResInfo by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            resource.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        IconButton(
                            onClick = { showResInfo = true },
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Info, contentDescription = "Info")
                        }
                    }
                    Row() {
                        val usedNum = resource.maxUses - resource.usesLeft
                        for (i in 1..usedNum) {
                            Checkbox(
                                checked = true,
                                onCheckedChange = {
                                    model.changeResUsesLeft(
                                        characterId,
                                        resource.id,
                                        (resource.usesLeft + 1)
                                    )
                                }
                            )
                        }

                        for (i in 1..resource.usesLeft) {
                            Checkbox(
                                checked = false,
                                onCheckedChange = {
                                    model.changeResUsesLeft(
                                        characterId,
                                        resource.id,
                                        (resource.usesLeft - 1)
                                    )
                                }
                            )
                        }
                    }

                    if (showResInfo) {

                        ResourceInfoPopup(
                            resource = resource,
                            onDismiss = { showResInfo = false },
                            onDelete = {
                                model.deleteResourceFromCharacter(characterId, resource.id)
                                showResInfo = false
                            }
                        )
                    }
                }
            }
        }
    }
}