import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    `maven-publish`
    signing
}

group = "top.limbang.minecraft"
version = "1.0.3"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation(kotlinx("serialization-json", serializationJsonVersion))

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutinesVersion")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


publishing {
    repositories {
        maven {
            name = "OSSRH"
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/limbang/yggdrasil")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "yggdrasil"
            from(components["java"])
            pom {
                name.set("yggdrasil")
                packaging = "jar"
                url.set("https://github.com/limbang/yggdrasil")
                description.set("Minecraft yggdrasil api")

                scm {
                    url.set("https://github.com/limbang/yggdrasil")
                    connection.set("git@github.com:limbang/yggdrasil.git")
                    developerConnection.set("https://github.com/limbang/yggdrasil.git")
                }

                licenses {
                    license {
                        name.set("GNU Affero General Public License v3.0")
                        url.set("https://choosealicense.com/licenses/agpl-3.0/")
                    }
                }

                developers {
                    developer {
                        id.set("limbang")
                        name.set("limbang")
                        email.set("495071565@qq.com")
                    }
                }

            }
        }
    }
}

signing {
    val signingKeyId: String? by project
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
