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