plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.piggie.iemtrigger"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.piggie.iemtrigger"
        minSdk = 34
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
            }
            isMinifyEnabled = true          // 开启代码混淆与压缩
            isShrinkResources = true        // 剔除未使用的资源
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}