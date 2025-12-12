import java.util.Properties

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

val localProps = Properties()
val localPropsFile = rootDir.resolve("local.properties")
if (localPropsFile.exists()) {
    localPropsFile.inputStream().use { localProps.load(it) }
}

val mapboxDownloadsToken: String =
    localProps.getProperty("MAPBOX_DOWNLOADS_TOKEN")
        ?: System.getenv("MAPBOX_DOWNLOADS_TOKEN")
        ?: throw GradleException("Missing MAPBOX_DOWNLOADS_TOKEN. Add it to local.properties or set env var.")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://api.mapbox.com/downloads/v2/releases/maven") {
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password = mapboxDownloadsToken
            }
        }
    }
}

rootProject.name = "BokoAR"
include(":app")
