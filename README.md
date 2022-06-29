<p align="center">
  <img src="https://user-images.githubusercontent.com/7759511/176542504-8267c132-75b8-433f-b0bd-850fa8242377.png">
</p>

<p align="center">
  <a href="https://github.com/holixfactory/bottomsheetdialog-compose/actions"><img src="https://github.com/holixfactory/bottomsheetdialog-compose/workflows/Publish/badge.svg" /></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/holixfactory/bottomsheetdialog-compose/releases"><img src="https://img.shields.io/github/v/release/holixfactory/bottomsheetdialog-compose" /></a>
</p>
<p align="center">Jetpack Compose BottomSheetDialog library that allows you to use BottomsheetDialog like Dialog's interface.</p>
<p align="center"> Also, it supports to set navigation bar color when BottomSheetDialog has shown.</p>

# Gradle
```gradle
// module's build.gradle
dependencies {
    implementation "com.holix.android:bottomsheetdialog-compose:1.0.1"
}
```
# Usage
```kotlin
@Composable
fun YourComposable() {
    var show by remember {
        mutableStateOf(false)
    }
    if (show) {
        BottomSheetDialog(
            onDismissRequest = {
                show = false
            },
            properties = BottomSheetDialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                navigationBarColor = Color.Unspecified
            )
        ) {
            // content
            Surface {
                ...
            }
        }
    }
}

```
