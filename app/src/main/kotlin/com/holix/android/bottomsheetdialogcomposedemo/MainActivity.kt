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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import com.holix.android.bottomsheetdialogcomposedemo.preferences.*

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
                var showBottomSheetDialog by remember {
                    mutableStateOf(false)
                }
                // BottomSheetProperties
                var dismissOnBackPress by remember {
                    mutableStateOf(true)
                }
                var dismissOnClickOutside by remember {
                    mutableStateOf(true)
                }
                var dismissWithAnimation by remember {
                    mutableStateOf(false)
                }
                // NavigationBarProperties
                val surfaceColor = MaterialTheme.colors.surface
                var navigationBarColor by remember(surfaceColor) {
                    mutableStateOf(surfaceColor)
                }
                var darkIcons by remember {
                    mutableStateOf(DarkIconsValue.Default)
                }
                var navigationBarContrastEnforced by remember {
                    mutableStateOf(true)
                }
                // BottomSheetBehaviorProperties
                var state by remember {
                    mutableStateOf(BottomSheetBehaviorProperties.State.Collapsed)
                }
                var maxWidth by remember {
                    mutableStateOf(BottomSheetBehaviorProperties.Size.NotSet)
                }
                var maxHeight by remember {
                    mutableStateOf(BottomSheetBehaviorProperties.Size.NotSet)
                }
                var isDraggable by remember {
                    mutableStateOf(true)
                }
                var expandedOffset by remember {
                    mutableStateOf(0)
                }
                var halfExpandedRatio by remember {
                    mutableStateOf(0.5F)
                }
                var isHideable by remember {
                    mutableStateOf(true)
                }
                var peekHeight by remember {
                    mutableStateOf(BottomSheetBehaviorProperties.PeekHeight.Auto)
                }
                var isFitToContents by remember {
                    mutableStateOf(true)
                }
                var skipCollapsed by remember {
                    mutableStateOf(false)
                }
                var isGestureInsetBottomIgnored by remember {
                    mutableStateOf(false)
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
                            ),
                            behaviorProperties = BottomSheetBehaviorProperties(
                                state = state,
                                maxWidth = maxWidth,
                                maxHeight = maxHeight,
                                isDraggable = isDraggable,
                                expandedOffset = expandedOffset,
                                halfExpandedRatio = halfExpandedRatio,
                                isHideable = isHideable,
                                peekHeight = peekHeight,
                                isFitToContents = isFitToContents,
                                skipCollapsed = skipCollapsed,
                                isGestureInsetBottomIgnored = isGestureInsetBottomIgnored
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
                                repeat(30) { index ->
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
                                                "Default (color.luminance() > 0.5F)"
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
                            PreferenceCategory("BottomSheetBehaviorProperties")
                            SingleChoicePreference(
                                value = state.name,
                                onValueChange = {
                                    state = BottomSheetBehaviorProperties.State.valueOf(it)
                                },
                                label = "state",
                                options = BottomSheetBehaviorProperties.State.values().map { value ->
                                    Pair(value.name, value.name)
                                }
                            )
                            IntPreference(
                                value = maxWidth.value,
                                onValueChange = {
                                    maxWidth = BottomSheetBehaviorProperties.Size(it)
                                },
                                label = "maxWidth (px)",
                                helpText = "Default: not set (-1)",
                                description = { maxWidth ->
                                    if (maxWidth == -1) {
                                        "not set (-1)"
                                    } else {
                                        "${maxWidth}px"
                                    }
                                },
                                validate = { maxWidth -> maxWidth == -1 || maxWidth > 0 }
                            )
                            IntPreference(
                                value = maxHeight.value,
                                onValueChange = {
                                    maxHeight = BottomSheetBehaviorProperties.Size(it)
                                },
                                label = "maxHeight (px)",
                                helpText = "Default: not set (-1)",
                                description = { maxHeight ->
                                    if (maxHeight == -1) {
                                        "not set (-1)"
                                    } else {
                                        "${maxHeight}px"
                                    }
                                },
                                validate = { maxHeight -> maxHeight == -1 || maxHeight > 0 }
                            )
                            BooleanPreference(
                                value = isDraggable,
                                onValueChange = {
                                    isDraggable = it
                                },
                                label = "isDraggable"
                            )
                            IntPreference(
                                value = expandedOffset,
                                onValueChange = {
                                    expandedOffset = it
                                },
                                label = "expandedOffset (px)",
                                helpText = "expandedOffset >= 0",
                                description = { expandedOffset ->
                                    if (expandedOffset == 0) {
                                        "Default (0)"
                                    } else {
                                        "${expandedOffset}px"
                                    }
                                },
                                validate = { expandedOffset -> expandedOffset >= 0 }
                            )
                            FloatPreference(
                                value = halfExpandedRatio,
                                onValueChange = {
                                    halfExpandedRatio = it
                                },
                                label = "halfExpandedRatio",
                                helpText = "0 < halfExpandedRatio < 1",
                                description = { halfExpandedRatio ->
                                    if (halfExpandedRatio == 0.5F) {
                                        "Default (0.5)"
                                    } else {
                                        "$halfExpandedRatio"
                                    }
                                },
                                validate = { halfExpandedRatio ->
                                    0 < halfExpandedRatio && halfExpandedRatio < 1
                                }
                            )
                            BooleanPreference(
                                value = isHideable,
                                onValueChange = {
                                    isHideable = it
                                },
                                label = "isHideable"
                            )
                            IntPreference(
                                value = peekHeight.value,
                                onValueChange = {
                                    peekHeight = BottomSheetBehaviorProperties.PeekHeight(it)
                                },
                                label = "peekHeight (px)",
                                helpText = "peekHeight > 0",
                                description = { peekHeight ->
                                    if (peekHeight == -1) {
                                        "Auto (-1)"
                                    } else {
                                        "${peekHeight}px"
                                    }
                                },
                                validate = { peekHeight -> peekHeight == -1 || peekHeight > 0 }
                            )
                            BooleanPreference(
                                value = isFitToContents,
                                onValueChange = {
                                    isFitToContents = it
                                },
                                label = "isFitToContents"
                            )
                            BooleanPreference(
                                value = skipCollapsed,
                                onValueChange = {
                                    skipCollapsed = it
                                },
                                label = "skipCollapsed"
                            )
                            BooleanPreference(
                                value = isGestureInsetBottomIgnored,
                                onValueChange = {
                                    isGestureInsetBottomIgnored = it
                                },
                                label = "isGestureInsetBottomIgnored"
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
