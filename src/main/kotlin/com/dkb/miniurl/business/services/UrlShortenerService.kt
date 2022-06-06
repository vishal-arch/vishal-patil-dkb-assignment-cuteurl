package com.dkb.miniurl.business.services

import com.dkb.miniurl.business.entities.UrlMetadata
import com.dkb.miniurl.business.repositories.UrlShortenerRepository
import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import com.dkb.miniurl.controller.response.ShortenedUrlResponse
import com.dkb.miniurl.mapper.UrlMapper
import com.dkb.miniurl.metrics.UrlShorteningMetrics
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.mapstruct.factory.Mappers
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

/**
 * This is a Service class that has methods related to Shortening and Fetching the actual redirect URL.
 */
@Service
@Transactional
class UrlShortenerService(
    val urlShortenerRepository: UrlShortenerRepository,
    val urlShorteningMetrics: UrlShorteningMetrics
) {

    val logger = LoggerFactory.logger(UrlShortenerService::class.java)

    val mapper = Mappers.getMapper(UrlMapper::class.java)

    /**
     *  The method is responsible to convert & persist the Long Url passed in the request to shortenedUrl
     *  The flow of how the method works is as follows
     *  1) Decodes the reuest URL
     *  2) Generates HashCode and check if the record exiists in the DB with the hashcode
     *  3) If found, return the Mapped response
     *  4) If not found, Maps the [ShortenedUrlRequest] to [UrlMetadata] saves the DB and returns the Mapped response
     *
     *  @param shortenedUrlRequest [ShortenedUrlRequest] with input request Url.
     *  @param baseUrl, The base URL of the request
     *  @return the method returns the mapped [ShortenedUrlResponse]
     */
    @Cacheable(value = ["urlMetadataCache"], keyGenerator = "customKeyGenerator")
    fun convertToShortUrl(
        shortenedUrlRequest: ShortenedUrlRequest,
        baseUrl: String
    ): ShortenedUrlResponse {

        decodeUrl(shortenedUrlRequest)
        val urlMetadata = mapper.toUrlMetaData(shortenedUrlRequest)
        var fetchedUrlMetadata = getUrlDetails(urlMetadata.hash)

        return fetchedUrlMetadata?.run {
            mapper.toShortenedUrlResponse(fetchedUrlMetadata, baseUrl)
        } ?: run {
            val persistedUrlMetadata = urlShortenerRepository.save(urlMetadata)
            createCountMetrics(persistedUrlMetadata.shortUrl)
            logger.info("New shortUrl persisted. shortUrl generate is ${persistedUrlMetadata.shortUrl} ")
            return mapper.toShortenedUrlResponse(persistedUrlMetadata, baseUrl);
        }

    }

    /**
     * The method returns the Actual Url for the shortUrl passed
     * @param shortenedUrl from the api request
     * @return [ShortenedUrlResponse] with longUrl that the user would be redirected to.
     */
    @Cacheable(value = ["RedirectUrlCache"], key = "#shortenedUrl")
    fun getActualRedirectUrl(shortenedUrl: String): ShortenedUrlResponse {

        var urlMetadata = getUrlDetailsByShortUrl(shortenedUrl)
        incrementCounter(shortenedUrl)
        return mapper.toShortenedUrlResponse(urlMetadata, "");
    }

    private fun getUrlDetails(hash: String): UrlMetadata? {
        return urlShortenerRepository.findByHash(hash)
    }

    private fun getUrlDetailsByShortUrl(shortUrl: String): UrlMetadata {

        return urlShortenerRepository.findByShortUrl(shortUrl)
            ?: run {
                logger.info("The shortUrl passed not found,Cannot redirect for Url ${shortUrl}")
                throw EntityNotFoundException("Cannot redirect for Url ${shortUrl}")
            }

    }

    private fun decodeUrl(shortenedUrlRequest: ShortenedUrlRequest) {
        shortenedUrlRequest.url =
            URLDecoder.decode(shortenedUrlRequest.url, StandardCharsets.UTF_8);
    }

    private fun createCountMetrics(tag: String) {
        urlShorteningMetrics.createCounterMetricsForLinkClicked(tag)
    }

    private fun incrementCounter(tag: String) {
        urlShorteningMetrics.incrementLinkClickedCounter(tag)
    }

}