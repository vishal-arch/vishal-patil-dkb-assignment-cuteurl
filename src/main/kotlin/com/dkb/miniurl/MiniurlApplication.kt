package com.dkb.miniurl

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
    info = Info(
        title = "Miniurl service to Encoding & Decoding of Url's",
        version = "1.0",
        description = "Miniurl is a service that takes the input Url from the user and encodes it to a smaller representation."
    )
)
@SpringBootApplication
class MiniurlApplication

fun main(args: Array<String>) {
    runApplication<MiniurlApplication>(*args)
}
