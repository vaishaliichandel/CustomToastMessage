plugins {
    alias(libs.plugins.kotlin.android)
    id ("maven-publish")
    id ("com.android.library")


}

android {
    namespace = "com.vaishali.customtoast"
    compileSdk = 35

    defaultConfig {
//        applicationId = "com.vaishali.customtoast"
        minSdk = 24
        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"

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

    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.vaishaliichandel"
            artifactId = "CustomToastMessage"
            version = "1.0.3"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}