pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "Shuvi"
include("Shuvi-API", "Shuvi-Server")
