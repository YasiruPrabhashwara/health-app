plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

kotlin {
    jvmToolchain(8)
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
