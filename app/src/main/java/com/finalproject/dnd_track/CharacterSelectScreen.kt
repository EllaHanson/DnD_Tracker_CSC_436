package com.finalproject.dnd_track

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.dnd_track.ui.AddCharacterPopup
import com.finalproject.dnd_track.ui.EditCharacterPopup
import com.finalproject.dnd_track.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSelectScreen(
    characters: List<Character>,
    onCharacterSelected: (Character) -> Unit,
    onAddCharacter: (name: String, characterClass: String, maxHp: Int, resources: List<Resource>) -> Unit = { _, _, _, _ -> },
    onUpdateCharacter: (id: Int, name: String, characterClass: String, maxHp: Int) -> Unit = { _, _, _, _ -> }
) {
    var showAddPopup by remember { mutableStateOf(false) }
    var clickedCharacter by remember { mutableStateOf<Character?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Your Characters",
                    style = MaterialTheme.typography.headlineLarge)
                        },
                actions = {
                    IconButton(onClick = { showAddPopup = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Character")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(characters) { character ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCharacterSelected(character) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        Column {
                            Text(
                                text = character.name,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.offset(y = (4).dp),
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = character.characterClass,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.offset(y = (-10).dp)
                            )

                            Text(
                                text = "${character.resources.size} resources",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.offset(y = (-14).dp)

                            )
                        }

                        IconButton(
                            onClick = { clickedCharacter = character },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(40.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit_character),
                                contentDescription = "Edit Character",
                                modifier = Modifier.fillMaxSize(0.65f)
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddPopup) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        AddCharacterPopup(
            onConfirm = { name, characterClass, maxHp, resources ->
                onAddCharacter(name, characterClass, maxHp, resources)
                showAddPopup = false
            },
            onDismiss = { showAddPopup = false }
        )
    }

    if (clickedCharacter != null) {
        EditCharacterPopup(
            character = clickedCharacter!!,
            onConfirm = { name, characterClass, maxHp ->
                onUpdateCharacter(clickedCharacter!!.id, name, characterClass, maxHp)
                clickedCharacter = null
            },
            onDismiss = { clickedCharacter = null }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterSelectPreview() {
    val sampleCharacters = listOf(
        Character(id = 0, name = "Thorin", characterClass = "Barbarian",
            resources = listOf(Resource(id = 0, name = "Rage", usesLeft = 2, maxUses = 2)),
            currHp = 18, maxHp = 18),
        Character(id = 1, name = "Elara", characterClass = "Druid",
            resources = listOf(Resource(id = 0, name = "Wildshape", usesLeft = 3, maxUses = 3)),
            currHp = 9, maxHp = 12)
    )
    AppTheme {
        CharacterSelectScreen(characters = sampleCharacters, onCharacterSelected = {})
    }
}