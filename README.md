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
    implementation "com.holix.android:bottomsheetdialog-compose:1.0.3"
}
```
## Usage
You can use BottomSheetDialog composable like Dialog composable! It is super simple 😎
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
                ...
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
## BottomSheetDialogProperties
|name|default value|type|
|--|--|--|
|dismissOnBackPress|true|Boolean|
|dismissOnClickOutside|true|Boolean|
|dismissWithAnimation|false|Boolean|
|enableEdgeToEdge|false|Boolean|
|navigationBarProperties|NavigationBarProperties()|NavigationBarProperties|
|behaviorProperties|BottomSheetBehaviorProperties()|BottomSheetBehaviorProperties|

### NavigationBarProperties
inspired by [accompanist's SystemUiController](https://github.com/google/accompanist/blob/353be641be03ffed5dc2a89efc6fdcb0e6fe65b1/systemuicontroller/src/main/java/com/google/accompanist/systemuicontroller/SystemUiController.kt#L97-L119)

|name|default value|type|
|--|--|--|
|color|Color.Unspecified|Color|
|darkIcons|color.luminance() > 0.5f|Boolean|
|navigationBarContrastEnforced|true|Boolean|
|transformColorForLightContent|{ original -> Color(0f, 0f, 0f, 0.3f).compositeOver(original) }|(Color) -> Color|

### BottomSheetBehaviorProperties
[BottomSheetBehavior official docs](https://developer.android.com/reference/com/google/android/material/bottomsheet/BottomSheetBehavior)

|name|default value|type|
|--|--|--|
|state|State.Collapsed|[State](https://github.com/holixfactory/bottomsheetdialog-compose/blob/5dbbc8cb1ef4b9ec27d4181e87d3136dd2915216/bottomsheetdialog-compose/src/main/kotlin/com/holix/android/bottomsheetdialog/compose/BottomSheetDialog.kt#L156)|
|maxWidth|Size.NotSet|[Size](https://github.com/holixfactory/bottomsheetdialog-compose/blob/5dbbc8cb1ef4b9ec27d4181e87d3136dd2915216/bottomsheetdialog-compose/src/main/kotlin/com/holix/android/bottomsheetdialog/compose/BottomSheetDialog.kt#L167)|
|maxHeight|Size.NotSet|[Size](https://github.com/holixfactory/bottomsheetdialog-compose/blob/5dbbc8cb1ef4b9ec27d4181e87d3136dd2915216/bottomsheetdialog-compose/src/main/kotlin/com/holix/android/bottomsheetdialog/compose/BottomSheetDialog.kt#L167)|
|isDraggable|true|Boolean|
|expandedOffset|0|Integer|
|halfExpandedRatio|0.5F|Float|
|isHideable|true|Boolean|
|peekHeight|PeekHeight.Auto|[PeekHeight](https://github.com/holixfactory/bottomsheetdialog-compose/blob/5dbbc8cb1ef4b9ec27d4181e87d3136dd2915216/bottomsheetdialog-compose/src/main/kotlin/com/holix/android/bottomsheetdialog/compose/BottomSheetDialog.kt#L177)|
|isFitToContents|true|Boolean|
|skipCollapsed|false|Boolean|
|isGestureInsetBottomIgnored|false|Boolean|

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
