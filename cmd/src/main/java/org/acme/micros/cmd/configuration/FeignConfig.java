package org.acme.micros.cmd.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@EnableFeignClients("org.acme.micros.cmd.client")
class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Logger feignLogger() {
        return new Slf4jLogger();
    }

    @Bean
    RequestInterceptor requestInterceptor() {
        return requestTemplate ->
                 requestTemplate
                   .header(ACCEPT, APPLICATION_JSON_VALUE)
                   .header(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    }
}
