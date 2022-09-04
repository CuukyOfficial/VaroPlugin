import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Base64

plugins {
    id("java-library")
    id("maven-publish")
}

group = "de.varoplugin"
version = "5.0.0-ALPHA-1"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
    withJavadocJar()
}

tasks.compileJava { options.encoding = "UTF-8" }
tasks.javadoc { options.encoding = "UTF-8" }

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.varoplugin.de/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

val internal: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
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
    modularInternal("de.varoplugin:CFW:1.0.0", "CFW")
    implementation("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.googlecode.json-simple:json-simple:1.1.1")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:6.1.3-SNAPSHOT")
    compileOnly("com.github.labymod:legacy-labymod-server-api:1.0")
    compileOnly("me.clip:placeholderapi:2.11.1")
    runtimeDownload("net.dv8tion:JDA:5.0.0-alpha.18") {
        exclude(module = "opus-java")
    }
    runtimeDownload("net.kyori:adventure-text-minimessage:4.11.0")
    runtimeDownload("net.kyori:adventure-platform-bukkit:4.1.1")
    runtimeDownload("me.carleslc.Simple-YAML:Simple-Yaml:1.8")
    runtimeDownload("org.yaml:snakeyaml:1.30")
    runtimeDownload("com.j256.ormlite:ormlite-jdbc:6.1")
    runtimeDownload("com.h2database:h2:2.1.214")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.jar {
    if (project.hasProperty("destinationDir"))
        destinationDirectory.set(file(project.property("destinationDir").toString()))
    if (project.hasProperty("fileName"))
        archiveFileName.set(project.property("fileName").toString())

    manifest {
        attributes(Pair("Manifest-Version", "1.0"), Pair("Class-Path", "."), Pair("Main-Class", "de.varoplugin.varo.RunnableLauncher"))
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(internal.map { if (it.isDirectory) it else zipTree(it) })

    val dependenciesFile = file("${buildDir}/dependencies.txt")
    val writer = dependenciesFile.bufferedWriter(charset = StandardCharsets.UTF_8)
    runtimeDownload.resolvedConfiguration.firstLevelModuleDependencies.forEach {resolvedDependency ->
        resolvedDependency.allModuleArtifacts.forEach {
            writer.write("${resolvedDependency.moduleName}:${it.moduleVersion}:${it.file.sha512().base64()}")
            writer.newLine()
        }
    }
    writer.close()
    from(dependenciesFile)
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
                username = project.findProperty("repouser") as? String
                password = project.findProperty("repopassword") as? String
            }
        }
    }
}

val mdSha512: MessageDigest = MessageDigest.getInstance("SHA-512")
fun File.sha512() : ByteArray = mdSha512.digest(this.readBytes())
fun ByteArray.base64() : String = Base64.getEncoder().encodeToString(this)
