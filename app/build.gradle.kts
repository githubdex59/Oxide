plugins {
    application
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(libs.guava)
    implementation("com.github.javaparser:javaparser-symbol-solver-core:3.26.4")
    implementation("com.github.javaparser:javaparser-core:3.26.4")
    implementation("com.github.javaparser:javaparser-core-serialization:3.26.4")
}

application {
    mainClass = "net.tannhauser.oxide.Main"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    isEnabled = false
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "net.tannhauser.oxide.Main"
    }
    from(sourceSets.main.get().output)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}