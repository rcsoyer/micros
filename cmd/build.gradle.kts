plugins {
    id("org.acme.micros.java-conventions")
}

dependencies {
    implementation("info.picocli:picocli-spring-boot-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework:spring-webmvc")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("net.logstash.logback:logstash-logback-encoder")
    runtimeOnly("io.github.openfeign:feign-okhttp")
    runtimeOnly("com.github.ben-manes.caffeine:caffeine")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

description = "cmd"

springBoot.buildInfo()
