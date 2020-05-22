import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

val kotlin_version: String by extra
val ktor_version: String by extra
val logback_version: String by extra



plugins {
    application
    kotlin("jvm") version "1.3.70"
    kotlin("kapt") version "1.3.72"
}

group = "com.example"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-freemarker:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-auth-ldap:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("io.ktor:ktor-locations:$ktor_version")

    //exposed
    implementation("org.jetbrains.exposed:exposed-core:0.24.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.24.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.24.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.24.1")

    //db
    implementation("org.postgresql:postgresql:42.2.5")

    //cache
    implementation("org.ehcache:ehcache:3.8.1")

    //hikari
    implementation("com.zaxxer:HikariCP:3.4.5")

    //di
    implementation("org.koin:koin-ktor:2.1.5")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.6")

    testImplementation("org.koin:koin-test:2.1.5")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

buildscript {
    apply("versions.gradle.kts")
}


