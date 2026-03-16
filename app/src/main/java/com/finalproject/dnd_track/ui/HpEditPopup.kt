package com.finalproject.dnd_track.ui

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.res.painterResource
import com.finalproject.dnd_track.R

@Composable
fun HpEditPopup(
    currNum: Int,
    onCurrNumChange: (Int) -> Unit,
    isHealing: Boolean,
    onIsHealingChange: (Boolean) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Change Hit Points",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(top = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { onIsHealingChange(!isHealing) },
                    modifier = Modifier.size(100.dp)
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.7f),
                        painter = painterResource(
                            id = if (isHealing) R.drawable.hp_heal else R.drawable.hp_hurt
                        ),
                        contentDescription = if (isHealing) "Healing mode" else "Damage mode"
                    )
                }

                Text(
                    text = if (isHealing) "Healing" else "Taking Damage",
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = 100
                            value = currNum
                            setOnValueChangedListener { _, _, newVal ->
                                onCurrNumChange(newVal)
                            }
                        }
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}