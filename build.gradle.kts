plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

configurations.all {
    resolutionStrategy {
        force("com.google.android.gms:play-services-basement:18.2.0") // Replace with required versions
        force("com.google.android.gms:play-services-tasks:18.2.0") // Example
        }
}