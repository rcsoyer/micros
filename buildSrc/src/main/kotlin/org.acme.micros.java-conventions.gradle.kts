import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR

plugins {
    idea
    java
    jacoco
}

repositories {
    mavenCentral()
}

group = "org.acme.micros"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
    testLogging.events.addAll(listOf(FAILED, PASSED, SKIPPED, STANDARD_ERROR))
}

tasks.jacocoTestReport {
    reports {
        html.required.set(true)
        html.outputLocation.file("${layout.buildDirectory}/reports/coverage")
    }
}