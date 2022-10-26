package com.holix.android.bottomsheetdialogcomposedemo.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.*

@Composable
fun ColorPreference(
    value: Color,
    onValueChange: (Color) -> Unit,
    label: String
) {
    val surfaceColor = MaterialTheme.colors.surface
    val currentOnValueChange by rememberUpdatedState(onValueChange)
    var showColorPickerDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (showColorPickerDialog) {
        var targetColor by remember {
            mutableStateOf(value)
        }
        Dialog(
            onDismissRequest = { showColorPickerDialog = false }
        ) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val controller = rememberColorPickerController()
                    HsvColorPicker(
                        modifier = Modifier
                            .size(300.dp),
                        controller = controller,
                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                            targetColor = colorEnvelope.color
                        }
                    )
                    AlphaSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(35.dp),
                        controller = controller,
                    )
                    BrightnessSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(35.dp),
                        controller = controller,
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = targetColor)
                            .size(64.dp)
                    )
                    Text(text = "#" + Integer.toHexString(targetColor.toArgb()).uppercase(),)
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onValueChange(targetColor)
                            showColorPickerDialog = false
                        }
                    ) {
                        Text(text = "Apply")
                    }
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        when (value) {
            MaterialTheme.colors.surface -> {
                Text("Default - MaterialTheme.colors.surface")
                Spacer(modifier = Modifier.weight(1F))
                Button(
                    onClick = { showColorPickerDialog = true }
                ) {
                    Text("Set")
                }
            }
            else -> {
                Box(
                    modifier = Modifier
                        .background(value)
                        .size(24.dp)
                )
                Text(text = "#" + Integer.toHexString(value.toArgb()).uppercase())
                Button(
                    onClick = { currentOnValueChange(surfaceColor) }
                ) {
                    Text(text = "Reset")
                }
            }
        }
    }
}