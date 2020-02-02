package com.example.demo.problem

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import java.net.URI

class MyCustomProblem(value: String) : AbstractThrowableProblem(
        TYPE,
        "User not found",
        Status.NOT_FOUND,
        "User with username not found",
        null,
        null,
        mapOf("value" to value)
) {

    companion object {
        private val TYPE: URI = URI.create("about:blank")
    }

    override fun getCause(): Exceptional? {
        return null
    }
}