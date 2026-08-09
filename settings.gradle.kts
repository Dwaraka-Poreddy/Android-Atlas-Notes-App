pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AndroidAtlas-Notes"

// Feature modules
include(":feature:noteslist")
include(":feature:noteeditor")

// Core modules
include(":core:common")
include(":core:database")
include(":core:network")
include(":core:sync")
include(":core:designsystem")
include(":core:navigation")

include(":app")
