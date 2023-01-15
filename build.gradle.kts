plugins {
    id("java")
}

group = "xyz.cringee"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven ( "https://jitpack.io" )
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation ("com.github.BlueMap-Minecraft:BlueMapAPI:v2.4.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}