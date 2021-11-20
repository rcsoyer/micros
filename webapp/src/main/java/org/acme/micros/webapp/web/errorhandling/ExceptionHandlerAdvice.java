package org.acme.micros.webapp.web.errorhandling;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice
public class ExceptionHandlerAdvice implements ProblemHandling {
}
