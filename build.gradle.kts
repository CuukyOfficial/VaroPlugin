import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Base64

plugins {
    id("java-library")
    id("maven-publish")
}

group = "de.varoplugin"
version = "4.19.0-beta-8"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
    withJavadocJar()
}

tasks.compileJava { options.encoding = "UTF-8" }
tasks.javadoc { options.encoding = "UTF-8" }

sourceSets {
    main {
        java {
            srcDir("src")
        }
        resources {
            srcDir("resources")
        }
    }

    test {
        java {
            srcDir("test")
        }
    }
}

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.varoplugin.de/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

val internal: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
    isTransitive = false
}

val runtimeDownload: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

fun DependencyHandler.modularInternal(dependencyNotation: Any, localFileName: String) : Dependency? {
    val file = file("${rootDir}/libs/${localFileName}.jar")
    return if (file.exists())
        this.add("internal", files(file))
    else
        this.add("internal", dependencyNotation)
}

dependencies {
    modularInternal("de.varoplugin:CFW:0.6.19", "CFW-legacy")
    modularInternal("de.varoplugin:cfw:1.0.0-ALPHA-17", "CFW")

    implementation("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.googlecode.json-simple:json-simple:1.1.1")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.17")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("net.luckperms:api:5.4")

	runtimeDownload("com.github.cryptomorin:XSeries:11.3.0")
	runtimeDownload("com.google.guava:guava:33.3.0-jre")
    runtimeDownload("com.google.code.gson:gson:2.10.1")
    runtimeDownload("net.dv8tion:JDA:5.1.2") {
        exclude(module = "opus-java")
    }
    runtimeDownload("org.slf4j:slf4j-simple:2.0.16") {
        exclude(module = "slf4j-api")
    }
    runtimeDownload("com.github.pengrad:java-telegram-bot-api:6.9.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

val createDependenciesFile = tasks.register("createDependenciesFile") {
    mustRunAfter(tasks.getByName("compileJava"))
    doLast {
        val dependenciesFile = file("${buildDir}/dependencies.txt")
        val writer = dependenciesFile.bufferedWriter(charset = StandardCharsets.UTF_8)
        runtimeDownload.resolvedConfiguration.firstLevelModuleDependencies.forEach {resolvedDependency ->
            resolvedDependency.allModuleArtifacts.forEach {
                writer.write("${resolvedDependency.moduleName}:${it.moduleVersion}:${it.file.sha512().base64()}")
                writer.newLine()
            }
        }
        writer.close()
    }
}

tasks.jar {
    if (project.hasProperty("destinationDir"))
        destinationDirectory.set(file(project.property("destinationDir").toString()))
    if (project.hasProperty("fileName"))
        archiveFileName.set(project.property("fileName").toString())

    dependsOn(createDependenciesFile)

    manifest {
        attributes(Pair("Manifest-Version", "1.0"), Pair("Class-Path", "."), Pair("Main-Class", "de.cuuky.varo.MainLauncher"))
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(internal.map { if (it.isDirectory) it else zipTree(it) })

    from(file("${buildDir}/dependencies.txt"))
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        showStandardStreams = true
        outputs.upToDateWhen { false }
    }
}

tasks.processResources {
    outputs.upToDateWhen { false }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(sourceSets.main.get().resources.srcDirs) {
        include("**/plugin.yml")
        expand("name" to project.name, "version" to project.version)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("VaroPlugin")
                url.set("https://varoplugin.de")
            }
        }
    }

    repositories {
        maven {
            setUrl("https://repo.varoplugin.de/repository/maven-releases/")
            credentials {
                username = System.getenv("REPO_USER")
                password = System.getenv("REPO_PASSWORD")
            }
        }
    }
}

val mdSha512: MessageDigest = MessageDigest.getInstance("SHA-512")
fun File.sha512() : ByteArray = mdSha512.digest(this.readBytes())
fun ByteArray.base64() : String = Base64.getEncoder().encodeToString(this)
