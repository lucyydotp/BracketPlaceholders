import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version("7.0.0")
}

group = "net.lucypoulton"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.10")
    compileOnly("org.jetbrains:annotations:22.0.0")

    implementation("me.lucyy:squirtgun-api:2.0.0-pre5")
    compileOnly("net.kyori:adventure-text-serializer-legacy:4.9.2")
}

val shadowTask = tasks.withType<ShadowJar> {
    archiveClassifier.set("shadow")
    relocate("net.kyori", "me.clip.placeholderapi.libs.kyori")
    minimize()
    dependencies {
        exclude {
            it.moduleGroup != "net.lucypoulton" && it.moduleGroup != "me.lucyy"
        }
    }
}
tasks["build"].dependsOn(shadowTask)