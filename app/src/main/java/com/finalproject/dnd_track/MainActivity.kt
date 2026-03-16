package com.finalproject.dnd_track

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.*
import com.finalproject.dnd_track.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val model: ResViewModel = viewModel()
            val navController = rememberNavController()
            var selectedCharacter by remember { mutableStateOf<Character?>(null) }

            AppTheme {
                NavHost(navController = navController, startDestination = "characters") {
                    composable("characters") {
                        CharacterSelectScreen(
                            characters = model.characters,
                            onCharacterSelected = { character ->
                                selectedCharacter = character
                                navController.navigate("resources")
                            },
                            onAddCharacter = { name, characterClass, maxHp, resources ->
                                model.addCharacter(name, characterClass, maxHp, resources)
                            },
                            onUpdateCharacter = { id, name, characterClass, maxHp ->
                                model.updateCharacter(id, name, characterClass, maxHp)
                            }
                        )
                    }
                    composable("resources") {
                        selectedCharacter?.let { character ->
                            ResourceScreen(
                                character = character,
                                model = model,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}