package org.acme.micros.webapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.Year;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.acme.micros.webapp.domain.Person;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.acme.micros.webapp.configuration.CachingConfig.CACHE_PEOPLE_BANNED_YEARS;

/**
 * Source of truth for getting the banned years.
 * <p> The intention is that for certain business cases, some people ({@link Person}),
 * born in the banned years should not be considered
 */
@Slf4j
@Component
public class BannedYearsDefinition {

    @Value("banned-years")
    private ClassPathResource bannedYearsFile;

    /**
     * Get the banned-years from the local file
     *
     * @return Set unmodifiable of {@link Year#getValue()}
     * @throws UncheckedIOException wrapper of an original {@link IOException} if such happens while reading the
     *                              banned-years file
     * @implNote Since the banned-years file is local the result of this method is cached
     */
    @Cacheable(CACHE_PEOPLE_BANNED_YEARS)
    public Set<Integer> bannedYearsFromFile() {
        log.debug("Load the banned years info into the cache");

        try (final InputStream fileLines = bannedYearsFile.getInputStream()) {
            return IOUtils
              .readLines(fileLines, defaultCharset())
              .stream()
              .map(Year::parse)
              .map(Year::getValue)
              .collect(toUnmodifiableSet());
        } catch (IOException ioException) {
            log.error("Failure while reading the file with the people's banned years");
            throw new UncheckedIOException("Internal app error", ioException);
        }
    }
}
