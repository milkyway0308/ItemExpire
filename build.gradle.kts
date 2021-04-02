plugins {
    id("java")
    id("maven-publish")
    kotlin("jvm") version "1.4.30"
}

buildscript {
    repositories {
        mavenCentral()
    }
}

tasks {
    processResources {
        expand("version" to version)
    }
}

group = "skywolf46"
version = properties["version"] as String

repositories {
    mavenCentral()

    maven("https://maven.pkg.github.com/milkyway0308/CommandAnnotation") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }


    maven("https://maven.pkg.github.com/milkyway0308/ReflectedNBTWrapper") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }

    maven("https://maven.pkg.github.com/FUNetwork/SkywolfExtraUtility") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }


    maven("https://maven.pkg.github.com/FUNetwork/PlaceHolders") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }
}

dependencies {

    compileOnly("skywolf46:exutil:1.34.1") {
        isChanging = true
    }

    compileOnly("skywolf46:placeholders:latest.release") {
        isChanging = true
    }

    compileOnly("skywolf46:commandannotation:latest.release") {
        isChanging = true
    }
    compileOnly("skywolf46:refnbt:1.7.0-Alpha")
    compileOnly(files("V:/API/Java/Minecraft/Bukkit/Spigot/Spigot 1.12.2.jar"))
}

