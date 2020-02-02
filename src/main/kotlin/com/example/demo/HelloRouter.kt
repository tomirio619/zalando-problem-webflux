package com.example.demo

import com.example.demo.problem.MyCustomProblem
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import org.zalando.problem.Problem
import org.zalando.problem.Status
import org.zalando.problem.StatusType
import reactor.core.publisher.Mono

@Configuration
class HelloRouter {

    @Bean
    fun userRoutes() = router {
        ("/api").nest {

            accept(MediaType.APPLICATION_JSON).nest {
                "/hello".nest {
                    GET("/", ::helloGet)
                    GET("/problem", ::helloGetProblem)
                }
            }
        }
    }

    fun helloGet(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("Hello get!")
    }

    fun helloGetProblem(request: ServerRequest): Mono<ServerResponse> {
        return Mono.just("Hello Post")
                .map {throw Problem.builder()
                        .withTitle("Hello Problem")
                        .withStatus(Status.BAD_REQUEST)
                        .withDetail("A custom problem")
                        .build()
                }
                .map { throw UnsupportedOperationException() }
                .map { throw MyCustomProblem("Hello Problem!") }
                .flatMap {
                    ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("Hello!")
                }
    }

}