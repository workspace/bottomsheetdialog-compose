package com.holix.android.bottomsheetdialog.compose

object Versions {
    internal const val ANDROID_GRADLE_PLUGIN = "7.3.1"
    internal const val GRADLE_NEXUS_PUBLISH_PLUGIN = "1.1.0"
    internal const val KOTLIN = "1.7.20"

    internal const val MATERIAL = "1.8.0"
    internal const val ANDROIDX_CORE_KTX = "1.9.0"

    internal const val COMPOSE_BOM = "2023.01.00"
    const val COMPOSE_COMPILER = "1.3.2"
    internal const val ANDROIDX_ACTIVITY = "1.5.1"

    internal const val COLOR_PICKER = "1.0.0"
}

object Dependencies {
    const val androidGradlePlugin =
        "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
    const val gradleNexusPublishPlugin =
        "io.github.gradle-nexus:publish-plugin:${Versions.GRADLE_NEXUS_PUBLISH_PLUGIN}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"

    const val material = "com.google.android.material:material:${Versions.MATERIAL}"
    const val androidxActivityKtx = "androidx.activity:activity-ktx:${Versions.ANDROIDX_ACTIVITY}"
    const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX}"

    const val composeBom = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
    const val composeUI = "androidx.compose.ui:ui"
    const val composeRuntime = "androidx.compose.runtime:runtime"
    const val composeMaterial = "androidx.compose.material:material"
    const val composeFoundation = "androidx.compose.foundation:foundation"
    const val composeTooling = "androidx.compose.ui:ui-tooling"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.ANDROIDX_ACTIVITY}"

    const val colorPicker = "com.github.skydoves:colorpicker-compose:${Versions.COLOR_PICKER}"
}
