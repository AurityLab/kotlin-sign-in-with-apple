group = "com.auritylab"
version = "1.0.0"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.32"
    kotlin("plugin.serialization") version "1.4.32"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("org.jetbrains.dokka") version "0.10.0"

    id("maven-publish")
    id("signing")
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("com.auth0:java-jwt:3.10.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.create("sourceJar", Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.create("javadocJar", Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "kotlin-sign-in-with-apple"
            from(components["java"])
            artifact(tasks.getByName("sourceJar"))
            artifact(tasks.getByName("javadocJar"))

            repositories {
                maven {
                    url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                    credentials {
                        username = if (project.hasProperty("ossrhUsername"))
                            project.property("ossrhUsername") as String else null
                        password = if (project.hasProperty("ossrhPassword"))
                            project.property("ossrhPassword") as String else null
                    }
                }
            }

            pom {
                name.set("Kotlin Sign in with Apple")
                description.set("Sign in with Apple token verification written in Kotlin")
                url.set("https://github.com/AurityLab/kotlin-sign-in-with-apple")

                organization {
                    name.set("AurityLab UG (haftungsbeschraenkt)")
                    url.set("https://github.com/AurityLab")
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/AurityLab/kotlin-sign-in-with-apple/issues")
                }

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://github.com/AurityLab/kotlin-sign-in-with-apple/blob/master/LICENSE")
                        distribution.set("repo")
                    }
                }

                scm {
                    url.set("https://github.com/AurityLab/kotlin-sign-in-with-apple")
                    connection.set("scm:git:git://github.com/AurityLab/kotlin-sign-in-with-apple.git")
                    developerConnection.set("scm:git:ssh://git@github.com:AurityLab/kotlin-sign-in-with-apple.git")
                }

                developers {
                    developer {
                        id.set("WipeAir")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications.getByName("maven"))
}
