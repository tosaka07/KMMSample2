import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    id("com.android.library")
    id("kotlinx-serialization")
}

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    sourceSets["commonMain"].dependencies {
        implementation(Deps.Ktor.commonCore)
        implementation(Deps.Ktor.commonJson)
        implementation(Deps.Ktor.commonLogging)
        implementation(Deps.Ktor.commonSerialization)
        implementation(Deps.Coroutines.common) {
            version {
                strictly(Versions.coroutines)
            }
        }
        implementation("com.futuremind:koru:0.3.5")
        configurations.get("kapt").dependencies.add(
            org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
                "com.futuremind", "koru-processor", "0.3.5"
            )
        )
    }

    sourceSets["commonTest"].dependencies {
        implementation(Deps.KotlinTest.common)
        implementation(Deps.KotlinTest.annotations)
    }

    sourceSets["androidMain"].dependencies {
        implementation(Deps.material)
        implementation(Deps.Coroutines.android)
        implementation(Deps.Ktor.androidCore)
    }

    sourceSets["androidTest"].dependencies {
        implementation(Deps.KotlinTest.junit)
        implementation(Deps.junit)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Deps.Ktor.ios)
    }
    sourceSets["iosMain"].kotlin.srcDir("${buildDir.absolutePath}/generated/source/kaptKotlin/")


    sourceSets["iosTest"].dependencies {
    }
}

android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)