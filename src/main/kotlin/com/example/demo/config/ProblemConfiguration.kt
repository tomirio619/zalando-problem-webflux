package com.example.demo.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.WebExceptionHandler
import org.zalando.problem.ProblemModule
import org.zalando.problem.spring.webflux.advice.ProblemExceptionHandler
import org.zalando.problem.spring.webflux.advice.ProblemHandling
import org.zalando.problem.violations.ConstraintViolationProblemModule

@Component
class ProblemConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerModules(ProblemModule(), ConstraintViolationProblemModule())
    }

    /**
     * In WebFlux, if a request handler is not called, then the ControllerAdvice will not be used.
     * So for ResponseStatusAdviceTrait for a 404 Not found, MethodNotAllowedAdviceTrait, NotAcceptableAdviceTrait,
     * and UnsupportedMediaTypeAdviceTrait it is required to add a specific WebExceptionHandler:
     */
    @Bean
    @Order(-2) // The handler must have precedence over WebFluxResponseStatusExceptionHandler and Spring Boot's ErrorWebExceptionHandler
    fun problemExceptionHandler(mapper: ObjectMapper, problemHandling: ProblemHandling): WebExceptionHandler {
        return ProblemExceptionHandler(mapper, problemHandling)
    }

}
