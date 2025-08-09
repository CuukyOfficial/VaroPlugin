plugins {
    id("java-library")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
    withJavadocJar()
    
    // Disable Java version checks because Paper uses Java 21
    disableAutoTargetJvm()
}

tasks.compileJava { options.encoding = "UTF-8" }
tasks.javadoc { options.encoding = "UTF-8" }

repositories {
    mavenCentral()
}

val internal: Configuration by configurations.creating {
    configurations.compileOnly.get().extendsFrom(this)
    isTransitive = false
}

dependencies {
    internal(project(":varo-spigot"))
    internal(project(":varo-paper"))
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 8
}

tasks.jar {
    mustRunAfter(":varo-spigot:jar")
    mustRunAfter(":varo-paper:jar")

    if (project.hasProperty("destinationDir"))
        destinationDirectory.set(file(project.property("destinationDir").toString()))
    if (project.hasProperty("fileName"))
        archiveFileName.set(project.property("fileName").toString())

    manifest {
        attributes(Pair("Manifest-Version", "1.0"), Pair("Class-Path", "."), Pair("Main-Class", "de.varoplugin.varo.MainLauncher"))
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(internal.map { if (it.isDirectory) it else zipTree(it) })
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
            setUrl("https://repo.varoplugin.de/releases/")
            credentials {
                username = System.getenv("REPO_USER")
                password = System.getenv("REPO_PASSWORD")
            }
        }
    }
}
