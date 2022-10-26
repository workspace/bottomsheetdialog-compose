package com.holix.android.bottomsheetdialogcomposedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import com.holix.android.bottomsheetdialogcomposedemo.preferences.BooleanPreference
import com.holix.android.bottomsheetdialogcomposedemo.preferences.ColorPreference
import com.holix.android.bottomsheetdialogcomposedemo.preferences.PreferenceCategory
import com.holix.android.bottomsheetdialogcomposedemo.preferences.SingleChoicePreference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) {
                    darkColors()
                } else {
                    lightColors()
                }
            ) {
                var showBottomSheetDialog by rememberSaveable {
                    mutableStateOf(false)
                }
                // BottomSheetProperties
                var dismissOnBackPress by rememberSaveable {
                    mutableStateOf(true)
                }
                var dismissOnClickOutside by rememberSaveable {
                    mutableStateOf(true)
                }
                var dismissWithAnimation by rememberSaveable {
                    mutableStateOf(false)
                }
                // NavigationBarProperties
                val surfaceColor = MaterialTheme.colors.surface
                var navigationBarColor by remember(surfaceColor) {
                    mutableStateOf(surfaceColor)
                }
                var darkIcons by rememberSaveable {
                    mutableStateOf(DarkIconsValue.Default)
                }
                var navigationBarContrastEnforced by rememberSaveable {
                    mutableStateOf(true)
                }
                if (showBottomSheetDialog) {
                    BottomSheetDialog(
                        onDismissRequest = {
                            Log.d("[BottomSheetDialog]", "onDismissRequest")
                            showBottomSheetDialog = false
                        },
                        properties = BottomSheetDialogProperties(
                            dismissOnBackPress = dismissOnBackPress,
                            dismissOnClickOutside = dismissOnClickOutside,
                            dismissWithAnimation = dismissWithAnimation,
                            navigationBarProperties = NavigationBarProperties(
                                color = navigationBarColor,
                                darkIcons = when (darkIcons) {
                                    DarkIconsValue.Default -> navigationBarColor.luminance() > 0.5F
                                    DarkIconsValue.True -> true
                                    DarkIconsValue.False -> false
                                },
                                navigationBarContrastEnforced = navigationBarContrastEnforced
                            )
                        )
                    ) {
                        Surface(
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(text = "Title", style = MaterialTheme.typography.h5)
                                repeat(5) { index ->
                                    Text(
                                        text = "Item $index",
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                        }
                    }
                }
                Surface {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F)
                                .verticalScroll(rememberScrollState())
                        ) {
                            PreferenceCategory("BottomSheetDialogProperties")
                            BooleanPreference(
                                value = dismissOnBackPress,
                                onValueChange = {
                                    dismissOnBackPress = it
                                },
                                label = "dismissOnBackPress"
                            )
                            BooleanPreference(
                                value = dismissOnClickOutside,
                                onValueChange = {
                                    dismissOnClickOutside = it
                                },
                                label = "dismissOnClickOutside"
                            )
                            BooleanPreference(
                                value = dismissWithAnimation,
                                onValueChange = {
                                    dismissWithAnimation = it
                                },
                                label = "dismissWithAnimation"
                            )
                            PreferenceCategory("NavigationBarProperties")
                            ColorPreference(
                                value = navigationBarColor,
                                onValueChange = {
                                    navigationBarColor = it
                                },
                                label = "color"
                            )
                            SingleChoicePreference(
                                value = darkIcons.name,
                                onValueChange = {
                                    darkIcons = DarkIconsValue.valueOf(it)
                                },
                                label = "darkIcons",
                                options = DarkIconsValue.values().map { value ->
                                    Pair(
                                        value.name,
                                        when (value) {
                                            DarkIconsValue.Default ->
                                                "Default (color.luminance() > 0.5F) = " +
                                                        "${navigationBarColor.luminance() > 0.5F}"
                                            else -> value.name
                                        }
                                    )
                                }
                            )
                            BooleanPreference(
                                value = navigationBarContrastEnforced,
                                onValueChange = {
                                    navigationBarContrastEnforced = it
                                },
                                label = "navigationBarContrastEnforced"
                            )
                        }
                        Button(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            onClick = { showBottomSheetDialog = true }
                        ) {
                            Text(text = "Show")
                        }
                    }
                }
            }
        }
    }
}

private enum class DarkIconsValue {
    Default, True, False
}
