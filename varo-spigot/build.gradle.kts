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
    modularInternal(libs.cfw, "CFW")

    implementation(libs.spigot.oneeight)
    compileOnly(libs.json)
    compileOnly(libs.worldedit)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.luckperms)
    compileOnly(libs.floodgate)

	runtimeDownload(libs.slams.standalone) {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "annotations")
    }
    runtimeDownload(libs.slams.parser.jaskl) {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "slams-core")
        exclude(module = "annotations")
    }
    runtimeDownload(libs.slams.papi) {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "slams-core")
        exclude(module = "annotations")
    }
    runtimeDownload(libs.jaskl.yaml)
	runtimeDownload(libs.xseries)
	runtimeDownload(libs.guava)
    runtimeDownload(libs.gson)
    runtimeDownload(libs.commons.collections4)
    runtimeDownload(libs.commons.lang3)
    runtimeDownload(libs.jda) {
        exclude(module = "opus-java")
        exclude(module = "tink")
    }
    runtimeDownload(libs.slf4j) {
        exclude(module = "slf4j-api")
    }
    runtimeDownload(libs.bstats)

    testImplementation(libs.junit)
    testRuntimeOnly(libs.junitplatformlauncher)
    testImplementation(libs.commons.io)
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
