// Project-level build.gradle.kts
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

buildscript {
    dependencies {
        //noinspection UseTomlInstead
        classpath("com.google.gms:google-services:4.4.2") // Firebase services
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
