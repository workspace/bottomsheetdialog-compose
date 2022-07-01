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
MIT License

Copyright (c) 2022 HOLIX

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
