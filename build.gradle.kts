plugins {
    id("java")
}

group = "xyz.cringee"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven( "https://repo.bluecolored.de/releases")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0-M2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.0-M2")
    testImplementation("org.junit.platform:junit-platform-suite-api:1.11.0-M2")
    implementation("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.10.1")
    compileOnly ("de.bluecolored.bluemap:BlueMapAPI:2.7.2")
    testImplementation("org.mockito:mockito-core:5.12.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.withType<Test> {
    useJUnitPlatform()
}