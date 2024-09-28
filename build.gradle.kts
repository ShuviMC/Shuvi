import io.papermc.paperweight.util.constants.*

plugins {
    `java-library`
//  `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("io.papermc.paperweight.patcher") version "1.7.3"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") {
        content {
            onlyForConfigurations(PAPERCLIP_CONFIG)
        }
    }
}

dependencies {
    remapper("net.fabricmc:tiny-remapper:0.10.4:fat")
    decompiler("net.minecraftforge:forgeflower:2.0.674.3")
    paperclip("io.papermc:paperclip:3.0.3")
}

subprojects {
    apply(plugin = "java")
//  apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://ci.emc.gs/nexus/content/groups/aikar/")
        maven("https://repo.aikar.co/content/groups/aikar")
        maven("https://repo.md-5.net/content/repositories/releases/")
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://jitpack.io")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(17)
            options.compilerArgs.add("--enable-preview")
        }

        withType<Javadoc> {
            options.encoding = Charsets.UTF_8.name()
        }

        @Suppress("UnstableApiUsage")
        withType<ProcessResources> {
            filteringCharset = Charsets.UTF_8.name()
        }

        withType<Test> {
            minHeapSize = "2g"
            maxHeapSize = "2g"
        }
    }
}

paperweight {
    serverProject.set(project(":Shuvi-Server"))

    remapRepo.set("https://maven.fabricmc.net/")
    decompileRepo.set("https://files.minecraftforge.net/maven/")

    useStandardUpstream("purpur") {
        url.set(github("PurpurMC", "Purpur"))
        ref.set(providers.gradleProperty("purpurRef"))

        withStandardPatcher {
            baseName("Purpur")

            apiPatchDir.set(layout.projectDirectory.dir("patches/api"))
            apiOutputDir.set(layout.projectDirectory.dir("Shuvi-API"))

            serverPatchDir.set(layout.projectDirectory.dir("patches/server"))
            serverOutputDir.set(layout.projectDirectory.dir("Shuvi-Server"))
        }
    }
}
