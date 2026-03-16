package com.finalproject.dnd_track

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.finalproject.dnd_track.ui.AddCardPopup
import com.finalproject.dnd_track.ui.DnDAppBar
import com.finalproject.dnd_track.ui.HpCard
import com.finalproject.dnd_track.ui.HpEditPopup
import com.finalproject.dnd_track.ui.ResourceCards

@Composable
fun ResourceScreen(
    character: Character,
    model: ResViewModel,
    navController: NavHostController
) {
    // Look up the character live from the model so HP updates are reflected
    val liveCharacter = model.characters.find { it.id == character.id } ?: character

    var showHpPopup by remember { mutableStateOf(false) }
    var deltaHp by remember { mutableStateOf(0) }
    var isHealing by remember { mutableStateOf(true) }
    var showAddPopup by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DnDAppBar(
                title = liveCharacter.name,
                canNavigateBack = true,
                onUpClick = { navController.popBackStack() },
                canAdd = true,
                onAddClick = { showAddPopup = true }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            HpCard(
                currHp = liveCharacter.currHp,
                maxHp = liveCharacter.maxHp,
                onEdit = {
                    deltaHp = 0
                    isHealing = true
                    showHpPopup = true
                }
            )


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Class",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .height(32.dp)
                    )

                    Text(
                        text = liveCharacter.characterClass,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            ResourceCards(
                characterId = liveCharacter.id,
                resources = liveCharacter.resources,
                modifier = Modifier.fillMaxWidth(),
                model = model
            )
        }
    }

    if (showHpPopup) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        HpEditPopup(
            currNum = deltaHp,
            onCurrNumChange = { newValue -> deltaHp = newValue },
            isHealing = isHealing,
            onIsHealingChange = { isHealing = it },
            onConfirm = {
                model.changeCharHp(liveCharacter.id, deltaHp, isHealing)
                showHpPopup = false
            },
            onDismiss = { showHpPopup = false }
        )
    }

    if (showAddPopup) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        AddCardPopup(
            onConfirm = { resourceName, maxUses ->
                val maxUsesInt = maxUses.toIntOrNull() ?: 1

                val newResource = Resource(
                    id = 0,
                    name = resourceName,
                    usesLeft = maxUsesInt,
                    maxUses = maxUsesInt
                )

                model.addResourceToCharacter(liveCharacter.id, newResource)

                showAddPopup = false
            },
            onDismiss = { showAddPopup = false }
        )
    }
}