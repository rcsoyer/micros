package org.acme.micros.webapp.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

    public static final String CACHE_PEOPLE_BANNED_YEARS = "peopleBannedYears";
}