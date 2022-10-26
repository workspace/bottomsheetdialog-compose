package com.holix.android.bottomsheetdialogcomposedemo.preferences

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PreferenceCategory(
    label: String
) {
    Text(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        text = label,
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.ExtraBold
    )
}