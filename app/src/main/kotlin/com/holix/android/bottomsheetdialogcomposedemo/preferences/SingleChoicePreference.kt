package com.holix.android.bottomsheetdialogcomposedemo.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun SingleChoicePreference(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<Pair<String, String>>,
) {
    val currentOnValueChange by rememberUpdatedState(onValueChange)
    var showChoiceDialog by remember {
        mutableStateOf(false)
    }
    if (showChoiceDialog) {
        Dialog(
            onDismissRequest = { showChoiceDialog = false }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp)
            ) {
                Column {
                    options.forEach { (option, description) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    currentOnValueChange(option)
                                    showChoiceDialog = false
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = description,
                                color = if (option == value) {
                                    MaterialTheme.colors.primary
                                } else {
                                    Color.Unspecified
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showChoiceDialog = true }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        options
            .firstOrNull { (option, _) -> option == value }
            ?.let { (_, description) ->
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = Modifier.weight(1F),
                    text = description
                )
            }
    }
}