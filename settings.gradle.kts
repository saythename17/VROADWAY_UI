/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
val snapshotVersion : String? = System.getenv("COMPOSE_SNAPSHOT_ID")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    plugins {
        id ("com.android.application") version "7.1.0-alpha06"
        id ("com.android.library") version "7.1.0-alpha06"
        // No need to define the Hilt plugin here.
    }

    resolutionStrategy {
        eachPlugin {
            // Use `resolutionStrategy` to define the Hilt plugin and its coordinate
            if (requested.id.id == "dagger.hilt.android.plugin") {
                useModule("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        snapshotVersion?.let {
            println("https://androidx.dev/snapshots/builds/$it/artifacts/repository/") 
            maven { url = uri("https://androidx.dev/snapshots/builds/$it/artifacts/repository/") }
        }

        google()
        mavenCentral()
    }
}
rootProject.name = "Vroadway"
include(":app")


