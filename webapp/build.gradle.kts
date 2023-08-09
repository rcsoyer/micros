plugins {
    id("org.acme.micros.java-conventions")
}

val mapStructVersion: String by rootProject.extra

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.mapstruct:mapstruct")
    implementation("org.zalando:problem-spring-web")
    implementation("org.springdoc:springdoc-openapi-ui")
    implementation("org.springdoc:springdoc-openapi-data-rest")

    annotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")

    runtimeOnly("com.github.ben-manes.caffeine:caffeine")
    runtimeOnly("io.micrometer:micrometer-tracing")
    runtimeOnly("org.liquibase:liquibase-core")
    runtimeOnly("net.logstash.logback:logstash-logback-encoder")
    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")

    testAnnotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")
}

description = "web-app"

tasks.withType<JavaCompile>() {
    options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
    options.compilerArgs.add("-Amapstruct.unmappedTargetPolicy=IGNORE")
}

springBoot.buildInfo()