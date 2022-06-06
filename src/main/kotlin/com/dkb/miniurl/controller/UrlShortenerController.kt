package com.dkb.miniurl.controller

import com.dkb.miniurl.business.services.UrlShortenerService
import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import com.dkb.miniurl.controller.response.ShortenedUrlResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.net.URL
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.Pattern


@RestController
class UrlShortenerController(private val urlShortenerService: UrlShortenerService) :
    UrlShortenerApi {

    /**
     * @see UrlShortenerApi.createShortenedUrl(ShortenedUrlRequest)
     */
    override fun createShortenedUrl(
        @RequestBody @Valid request: ShortenedUrlRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<ShortenedUrlResponse> {
        return ResponseEntity.ok(
            urlShortenerService.convertToShortUrl(
                request,
                getURLBase(httpRequest)
            )
        )
    }

    /**
     * @see UrlShortenerApi.redirectUrl(String)
     */
    override fun redirectUrl(@Valid @Pattern(regexp = "^[A-Za-z0-9]+\$") @PathVariable shortUrl: String): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI(urlShortenerService.getActualRedirectUrl(shortUrl)))
            .build();
    }

    private fun getURLBase(request: HttpServletRequest): String {
        val requestURL = URL(request.requestURL.toString())
        val port = if (requestURL.port == -1) "" else ":" + requestURL.getPort()
        return (requestURL.getProtocol() + "://" + requestURL.getHost()).toString() + port
    }
}