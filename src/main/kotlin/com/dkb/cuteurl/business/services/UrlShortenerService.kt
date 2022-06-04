package com.dkb.cuteurl.business.services

import com.dkb.cuteurl.controller.request.ShortenedUrlRequest
import org.springframework.stereotype.Service

/**
 * This is a Service class that has methods related to Shortening and Fetching the actual redirect URL.
 */
@Service
class UrlShortenerService {

    fun convertToShortUrl(shortenedUrlRequest: ShortenedUrlRequest):String{
        return shortenedUrlRequest.url;
    }

    fun getActualRedirectUrl(shortenedUrl: String):String{

        return "http://google.com";
    }




}