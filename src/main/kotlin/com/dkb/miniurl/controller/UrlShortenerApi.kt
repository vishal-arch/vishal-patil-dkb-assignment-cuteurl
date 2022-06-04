package com.dkb.miniurl.controller

import com.dkb.miniurl.controller.UrlShortenerApi.UrlConstant.API_PATH
import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Url Shortener", description = "Api's for shortening and de-shortening of URL's")
@RequestMapping(API_PATH)
interface UrlShortenerApi {

    object UrlConstant {
        const val API_PATH = "/v1/api/short"
    }

    /**
     * <b>[POST] /v1/api/short</b><br>
     * The method creates a Shortened URL for the long URL passed
     *
     * @param request [ShortenedUrlRequest] containing the Long URL
     * @return the Shortened URL
     */
    @Operation(
        summary = "The Api represents a shortening of a Url provided.",
        responses = [ApiResponse(
            responseCode = "201",
            description = "Response with newly created shortened Url",
            content = arrayOf(Content(schema = Schema(implementation = String::class)))
        )],
        requestBody = RequestBody(
            required = true,
            content = arrayOf(
                Content(
                    schema = Schema(implementation = ShortenedUrlRequest::class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = arrayOf(
                        ExampleObject(
                            name = "An example request for creating a shortened Url .",
                            // language=json
                            value = """
                                {
                                  "url": "http://localhost:8080/abcd2"
                                }
                            """
                        )
                    )
                )
            )
        )
    )
    @PostMapping
    fun createShortenedUrl(request: ShortenedUrlRequest): ResponseEntity<String> = ResponseEntity
        .status(HttpStatus.NOT_IMPLEMENTED)
        .build()

    /**
     * <b>[GET] /v1/api/short/{shortUrl}</b><br>
     * The method redirect the page based on shortUrl received in the request
     *
     * @param shortUrl The short url passed as an input
     */
    @GetMapping("/{shortUrl}")
    fun getActualRedirectUrl(@PathVariable shortUrl: String): ResponseEntity<Void> = ResponseEntity
        .status(HttpStatus.NOT_IMPLEMENTED)
        .build()
}