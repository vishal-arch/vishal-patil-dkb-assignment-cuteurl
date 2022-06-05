package com.dkb.miniurl.controller

import com.dkb.miniurl.controller.UrlShortenerApi.UrlConstant.API_PATH
import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import com.dkb.miniurl.controller.response.ShortenedUrlResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

@Tag(name = "Api details", description = "Api's for encoding(shortening) and decoding of URL's")
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
     * @param httpRequest [HttpServletRequest]
     * @return the Shortened URL
     */
    @Operation(
        summary = "The Api represents a shortening of a Url provided.",
        responses = [ApiResponse(
            responseCode = "200",
            description = "Response with newly created shortened Url, Please use the attribute miniurl from the response",
            content = [Content(
                schema = Schema(implementation = ShortenedUrlResponse::class),
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = [
                    ExampleObject(
                        name = "Response received",
                        //language = "json"
                        value = """
                            {
                                "requestedUrl": "http://www.requesturl.io",
                                "hash": "95c049a3084ab35d430631304782ccff902c4f6600c2e61e11f6b0f691560c64",
                                "alias": "Dh2Ze47",
                                "createdAt": "2022-06-05T17:20:57.850965+05:30",
                                "miniurl": "http://localhost:8086/Dh2Ze47"
                            }
                        """
                    )
                ]
            )]
        )],
        requestBody = RequestBody(
            required = true,
            content = arrayOf(
                Content(
                    schema = Schema(implementation = ShortenedUrlRequest::class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            name = "An example request for creating a shortened Url .",
                            // language=json
                            value = """
                                {
                                  "url": "http://www.requesturl.io"
                                }
                            """
                        )
                    ]
                )
            )
        )
    )
    @PostMapping
    fun createShortenedUrl(
        request: ShortenedUrlRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<ShortenedUrlResponse> = ResponseEntity
        .status(HttpStatus.NOT_IMPLEMENTED)
        .build()

    /**
     * <b>[GET] /v1/api/short/{shortUrl}</b><br>
     * The method redirect the page based on shortUrl received in the request
     *
     * @param shortUrl The short url passed as an input
     */
    @Operation(
        summary = "The Api is used to redirect user to the Actual Url page for the input ShortUrl",
        responses = [ApiResponse(
            responseCode = "302",
            description = "Response of the Api is a redirection to the actual Url"
        ),
            ApiResponse(
                responseCode = "404",
                description = "Response provided if the passed shortUrl is incorrect with and no record is found in the System",
                content = [Content(
                    schema = Schema(implementation = ExceptionController.ErrorObject::class),
                    mediaType = APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            name = "An example response if entity is not found",
                            // language=json
                            value = """
                                {
                                  "title":"Error while searching for a not existing entity",
                                  "details":"Cannot redirect for Url <ShortUrl>"
                                }
                                
                            """
                        )
                    ]
                )]
            )]
    )
    @GetMapping("/{shortUrl}")
    fun redirectUrl(@PathVariable shortUrl: String): ResponseEntity<Void> = ResponseEntity
        .status(HttpStatus.NOT_IMPLEMENTED)
        .build()
}