package com.holix.android.bottomsheetdialogcomposedemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

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
                if (showBottomSheetDialog) {
                    BottomSheetDialog(
                        onDismissRequest = {
                            showBottomSheetDialog = false
                        },
                        properties = BottomSheetDialogProperties(
                            navigationBarColor = MaterialTheme.colors.surface
                        )
                    ) {
                        Surface(
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(text = "Title", style = MaterialTheme.typography.h5)
                                repeat(5) { index ->
                                    Text(text = "Item $index", style = MaterialTheme.typography.body1)
                                }
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { showBottomSheetDialog = true }) {
                        Text(text = "Show")
                    }
                }
            }
        }
    }
}
