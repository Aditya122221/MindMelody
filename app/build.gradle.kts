import java.util.Properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
}

val ApiKey: String = localProperties.getProperty("API_KEY") ?: ""
val ModelName: String = localProperties.getProperty("GEMINI_MODEL_NAME")?: ""

plugins {
        alias(libs.plugins.android.application)
        alias(libs.plugins.kotlin.android)
        alias(libs.plugins.firebase)
}

android {
        namespace = "com.example.mindmelody"
        compileSdk = 36

        defaultConfig {
                applicationId = "com.example.mindmelody"
                minSdk = 24
                targetSdk = 36
                versionCode = 1
                versionName = "1.0"
                buildConfigField("String", "API_KEY", "\"$ApiKey\"")
                buildConfigField("String", "GEMINI_MODEL_NAME", "\"$ModelName\"")

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildFeatures {
                viewBinding= true
                buildConfig = true
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
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
        }
        kotlinOptions {
                jvmTarget = "11"
        }
}

dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.kotlin.parcelize.runtime)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.auth)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.okhttp.logging)
        implementation(libs.generativeai)
}