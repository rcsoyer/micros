plugins {
    id("org.acme.micros.java-conventions")
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("io.micrometer:micrometer-tracing")
    runtimeOnly("net.logstash.logback:logstash-logback-encoder")
}

description = "microservice"
