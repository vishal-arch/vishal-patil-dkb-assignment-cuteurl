package com.dkb.miniurl.controller

import com.dkb.miniurl.business.services.UrlShortenerService
import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class UrlShortenerController(private val urlShortenerService: UrlShortenerService) :
    UrlShortenerApi {

    /**
     * @see UrlShortenerApi.createShortenedUrl(ShortenedUrlRequest)
     */
    override fun createShortenedUrl(@RequestBody request: ShortenedUrlRequest): ResponseEntity<String> {
        return ResponseEntity.ok(urlShortenerService.convertToShortUrl(request))
    }

    /**
     * @see UrlShortenerApi.getActualRedirectUrl(String)
     */
    override fun getActualRedirectUrl(shortUrl: String): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI(urlShortenerService.getActualRedirectUrl(shortUrl)))
            .build();
    }
}