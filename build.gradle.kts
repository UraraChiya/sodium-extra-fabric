plugins {
    id("java")
    id("fabric-loom") version ("1.7.2") apply (false)
}

val MINECRAFT_VERSION by extra { "1.21.1" }
val NEOFORGE_VERSION by extra { "21.1.23" }
val FABRIC_LOADER_VERSION by extra { "0.16.2" }
val FABRIC_API_VERSION by extra { "0.102.1+1.21.1" }

// This value can be set to null to disable Parchment.
val PARCHMENT_VERSION by extra { null }

// https://semver.org/
val MAVEN_GROUP by extra { "me.flashyreese.mods" }
val ARCHIVE_NAME by extra { "sodium-extra" }
val MOD_VERSION by extra { "0.6.0-beta.3" }
val SODIUM_VERSION by extra { "mc1.21-0.6.0-beta.2" }

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    group = MAVEN_GROUP
    version = createVersionString()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

subprojects {
    apply(plugin = "maven-publish")

    repositories {
        maven("https://maven.parchmentmc.org/")
        maven("https://api.modrinth.com/maven")
    }

    base {
        archivesName = "$ARCHIVE_NAME-${project.name}"
    }

    java.toolchain.languageVersion = JavaLanguageVersion.of(21)

    tasks.processResources {
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("version" to createVersionString()))
        }
    }

    version = createVersionString()
    group = "me.flashyreese.mods"

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    tasks.withType<GenerateModuleMetadata>().configureEach {
        enabled = false
    }
}

fun createVersionString(): String {
    val builder = StringBuilder()

    val isReleaseBuild = project.hasProperty("build.release")
    val buildId = System.getenv("GITHUB_RUN_NUMBER")

    if (isReleaseBuild) {
        builder.append(MOD_VERSION)
    } else {
        builder.append(MOD_VERSION.split('-')[0])
        builder.append("-snapshot")
    }

    builder.append("+mc").append(MINECRAFT_VERSION)

    if (!isReleaseBuild) {
        if (buildId != null) {
            builder.append("-build.$buildId")
        } else {
            builder.append("-local")
        }
    }

    return builder.toString()
}
