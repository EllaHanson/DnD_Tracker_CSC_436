package com.finalproject.dnd_track.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finalproject.dnd_track.ResViewModel

@Composable
fun HpCard(
    currHp: Int,
    maxHp: Int,
    modifier: Modifier = Modifier,
    onEdit: () -> Unit = {}
) {
    Card() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hit Points",
                    style = MaterialTheme.typography.displayMedium
                )

                Text(
                    text = "$currHp / $maxHp",
                    style = MaterialTheme.typography.displayLarge
                )
            }

            IconButton(
                onClick = onEdit,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit HP"
                )
            }
        }
    }
}