apply(plugin = "io.github.gradle-nexus.publish-plugin")

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.nexus) apply false
    alias(libs.plugins.spotless) apply false
}

tasks.register("clean")
    .configure {
        delete(rootProject.layout.buildDirectory)
    }

apply(from = "${rootDir}/scripts/publish-root.gradle")
