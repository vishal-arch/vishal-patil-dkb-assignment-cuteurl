package com.dkb.miniurl.business.services

import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import com.dkb.miniurl.mapper.UrlMapper
import com.dkb.miniurl.util.DateUtils
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * This is a Service class that has methods related to Shortening and Fetching the actual redirect URL.
 */
@Service
class UrlShortenerService {

    val mapper = Mappers.getMapper(UrlMapper::class.java)
    val URL_EXPIRATION_DAYS = 2L
    fun convertToShortUrl(shortenedUrlRequest: ShortenedUrlRequest):String{

        val urlMetadata = mapper.toUrlMetaData(shortenedUrlRequest)
        urlMetadata.expirationTimestamp = getExpirationDate()
        return "";
    }

    private fun getExpirationDate(): LocalDateTime{
        return DateUtils.addDaysToTs(URL_EXPIRATION_DAYS, LocalDateTime.now())
    }

    fun getActualRedirectUrl(shortenedUrl: String):String{

        return "http://google.com";
    }




}