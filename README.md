<p align="center">Jetpack Compose BottomSheetDialog library that allows you to use BottomsheetDialog like Dialog's interface.</p>
<p align="center"> Also, it supports to set navigation bar color when BottomSheetDialog has shown.</p>
<p align="center">
  <a href="https://github.com/holixfactory/bottomsheetdialog-compose/actions"><img src="https://github.com/holixfactory/bottomsheetdialog-compose/workflows/Publish/badge.svg" /></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/holixfactory/bottomsheetdialog-compose/releases"><img src="https://img.shields.io/github/v/release/holixfactory/bottomsheetdialog-compose" /></a>
</p>

# Preview
<p align="center">
  <img src="https://user-images.githubusercontent.com/7759511/176542504-8267c132-75b8-433f-b0bd-850fa8242377.png">
</p>

# Get Started
## Gradle
```gradle
// module's build.gradle
dependencies {
    implementation "com.holix.android:bottomsheetdialog-compose:1.0.1"
}
```
## Usage
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
                dismissWithAnimation = false,
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
## Additional Information
- This library depends on [material-components-android(BottomSheetDialog)](https://github.com/material-components/material-components-android/blob/master/lib/java/com/google/android/material/bottomsheet/BottomSheetDialog.java).

# License
```
Designed and developed by 2022 holixfactory

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
