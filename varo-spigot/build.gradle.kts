import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

plugins {
    id("java-library")
    id("maven-publish")
}

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
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.varoplugin.de/releases")
    maven("https://repo.varoplugin.de/mirrors")
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
    modularInternal("de.varoplugin:cfw:1.0.0-ALPHA-24", "CFW")

    implementation("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.googlecode.json-simple:json-simple:1.1.1")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.17")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("net.luckperms:api:5.5")
    compileOnly("org.geysermc.floodgate:api:2.2.4-SNAPSHOT")

	runtimeDownload("io.github.almighty-satan.slams:slams-standalone:1.2.1") {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "annotations")
    }
    runtimeDownload("io.github.almighty-satan.slams:slams-parser-jaskl:1.2.1") {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "slams-core")
        exclude(module = "annotations")
    }
    runtimeDownload("io.github.almighty-satan.slams:slams-papi:1.2.1") {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "slams-core")
        exclude(module = "annotations")
    }
    runtimeDownload("io.github.almighty-satan.jaskl:jaskl-yaml:1.6.3")
	runtimeDownload("com.github.cryptomorin:XSeries:13.3.1")
	runtimeDownload("com.google.guava:guava:33.4.8-jre")
    runtimeDownload("com.google.code.gson:gson:2.13.1")
    runtimeDownload("org.apache.commons:commons-collections4:4.5.0")
    runtimeDownload("org.apache.commons:commons-lang3:3.17.0")
    runtimeDownload("net.dv8tion:JDA:5.6.1") {
        exclude(module = "opus-java")
        exclude(module = "tink")
    }
    runtimeDownload("org.slf4j:slf4j-simple:2.0.16") {
        exclude(module = "slf4j-api")
    }
    runtimeDownload("org.bstats:bstats-bukkit:3.1.0")

	testImplementation("org.junit.jupiter:junit-jupiter:5.12.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("commons-io:commons-io:2.19.0")
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

tasks.withType<JavaCompile>().configureEach {
	options.release = 8
}

tasks.jar {
    dependsOn(createDependenciesFile)

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
        expand("version" to project.version)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("varo-spigot")
                url.set("https://varoplugin.de")
            }
        }
    }

    repositories {
        maven {
            setUrl("https://repo.varoplugin.de/releases/")
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
