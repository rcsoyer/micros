plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.freefair.lombok") version "8.6"
}

val mapStructVersion by extra { "1.5.5.Final" }
val resilience4JVersion by extra { "3.1.1" }
val feignOkHttpVersion by extra { "13.2.1" }
val caffeineVersion by extra { "3.1.8" }
val picocliSpringBootVersion by extra { "4.7.5" }
val zalandoProblemVersion by extra { "0.29.1" }
val springDocApiVersion by extra { "1.8.0" }
val logBackEncoderVersion by extra { "7.4" }
val testContainersVersion by extra { "1.19.7" }
val eurekaVersion by extra { "4.1.1" }
val feignVersion by extra { "4.1.1" }

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "io.freefair.lombok")

    dependencyManagement {
        dependencies {
            dependency("info.picocli:picocli-spring-boot-starter:$picocliSpringBootVersion")
            dependency("org.mapstruct:mapstruct:$mapStructVersion")
            dependency("org.zalando:problem-spring-web:$zalandoProblemVersion")
            dependency("org.springdoc:springdoc-openapi-ui:$springDocApiVersion")
            dependency("org.springdoc:springdoc-openapi-data-rest:$springDocApiVersion")
            dependency("net.logstash.logback:logstash-logback-encoder:$logBackEncoderVersion")
            dependency("org.testcontainers:junit-jupiter:$testContainersVersion")
            dependency("org.testcontainers:mysql:$testContainersVersion")
            dependency("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:$resilience4JVersion")
            dependency("io.github.openfeign:feign-okhttp:$feignOkHttpVersion")
            dependency("com.github.ben-manes.caffeine:caffeine:$caffeineVersion")
            dependency("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:$eurekaVersion")
            dependency("org.springframework.cloud:spring-cloud-starter-openfeign:$feignVersion")
            dependency("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:$eurekaVersion")
        }

    }
}