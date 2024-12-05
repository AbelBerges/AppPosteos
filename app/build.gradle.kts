plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.desarrollo.appposteos"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.desarrollo.appposteos"
        minSdk = 33
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
dependencies {
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("io.github.florent37:shapeofview:1.4.7")
    implementation ("com.github.parse-community.Parse-SDK-Android:bolts-tasks:4.3.0")
    implementation ("com.github.parse-community.Parse-SDK-Android:parse:4.3.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
}

