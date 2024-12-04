
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.tiffincart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tiffincart"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
       
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures.viewBinding = true
}

val roomVersion = "2.6.1"

dependencies {
    implementation(libs.picasso)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.appcompat)
    implementation(libs.lottie)
    implementation (platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.datastore.core.android)
    implementation(libs.support.annotations)
    implementation(libs.firebase.storage)

    // Stripe Android SDK
    implementation(libs.stripe.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // add the dependency for the Google AI client SDK for Android
    implementation(libs.generativeai)

    // Required for one-shot operations (to use `ListenableFuture` from Guava Android)
    implementation(libs.guava)

    // Required for streaming operations (to use `Publisher` from Reactive Streams)
    implementation(libs.reactive.streams)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)

    implementation(libs.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}


