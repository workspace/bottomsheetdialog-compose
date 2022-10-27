package com.holix.android.bottomsheetdialogcomposedemo.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun IntPreference(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String,
    helpText: String,
    description: (Int) -> String = { "$it" },
    validate: (Int) -> Boolean = { true },
) {
    val currentOnValueChange by rememberUpdatedState(onValueChange)
    var _value by remember(value) {
        mutableStateOf("$value")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = _value,
                        onValueChange = { _value = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = {
                            Text(text = helpText)
                        },
                        isError = !validate(_value.toIntOrNull() ?: 0),
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = validate(_value.toIntOrNull() ?: 0),
                        onClick = {
                            _value.toIntOrNull()?.let {
                                currentOnValueChange(it)
                            }
                            showDialog = false
                        }
                    ) {
                        Text(text = "Set")
                    }
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier.weight(1F),
            text = description(value)
        )
    }
}