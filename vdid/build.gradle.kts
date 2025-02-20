plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

android {
    namespace = "com.example.vdid"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources {
            excludes.add("**/libjnidispatch.a")
            excludes.add("**/jnidispatch.dll")
            excludes.add("**/libjnidispatch.jnilib")
            excludes.add("**/*.proto")
        }
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    relocate("com.innovatrics.dot.document", "com.suma.sombreado.innovatrics")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.example"
            artifactId = "vdid"
            version = "1.0.2"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.com.innovatrics.dot.document)
    implementation(libs.androidx.constraintlayout)
}
