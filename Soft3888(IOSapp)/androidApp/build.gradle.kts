plugins {
    id("com.android.application")
    kotlin("android")
    id ("kotlin-android-extensions")
    id("com.google.gms.google-services")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.ontask.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")

    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:1.1.0-beta01")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.compose.material:material:1.1.0-beta01")
    implementation("androidx.compose.material:material-icons-extended:1.0.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.8")
    implementation("com.google.android.gms:play-services-auth:20.3.0")

    implementation(platform("com.google.firebase:firebase-bom:31.0.0"))
    implementation("com.google.firebase:firebase-firestore-ktx:24.4.0")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("androidx.databinding:databinding-runtime:7.3.1")

    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.0-beta01")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.0-beta01")
    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha04")


    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
}

apply(plugin = "com.google.gms.google-services")
