package com.dkb.miniurl

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(info = Info(title = "Url Shortening & Decoding Api's", version = "1.0", description = "Api information below"))
@SpringBootApplication
class CuteurlApplication

fun main(args: Array<String>) {
	runApplication<CuteurlApplication>(*args)
}
