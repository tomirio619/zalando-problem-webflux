package com.example.demo

import org.springframework.web.bind.annotation.ControllerAdvice
import org.zalando.problem.spring.webflux.advice.ProblemHandling

@ControllerAdvice
class ExceptionHandler : ProblemHandling