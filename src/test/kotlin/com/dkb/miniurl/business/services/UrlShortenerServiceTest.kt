package com.dkb.miniurl.business.services

import com.dkb.miniurl.business.entities.UrlMetadata
import com.dkb.miniurl.business.repositories.UrlShortenerRepository
import com.dkb.miniurl.controller.UrlShortenerApi
import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import com.dkb.miniurl.controller.response.ShortenedUrlResponse
import com.dkb.miniurl.util.UrlEncoderDecoder
import com.dkb.miniurl.util.UrlHasher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

class UrlShortenerServiceTest {

    val url = "http://testservice.com"
    val baseUrl = "http://baseurl.com/"
    val urlShortenerRepository = mock(UrlShortenerRepository::class.java)
    val urlShortenerService = UrlShortenerService(urlShortenerRepository)

    @Test
    fun shouldSaveUrlMetadata() {
        val urlMetadata =
            UrlMetadata(
                0L,
                UrlHasher.hashString(url),
                url,
                UrlEncoderDecoder.encode(System.currentTimeMillis()),
                LocalDateTime.now()
            )
        `when`(urlShortenerRepository.save(any())).thenReturn(urlMetadata)
        val shortenedUrlRequest = ShortenedUrlRequest(url)
        var response = urlShortenerService.convertToShortUrl(shortenedUrlRequest, baseUrl)

        verify(urlShortenerRepository).save(any())
        assertResponse(response)
    }

    @Test
    fun shouldFetchExistingUrlMetadata() {
        val urlMetadata =
            UrlMetadata(
                0L,
                UrlHasher.hashString(url),
                url,
                UrlEncoderDecoder.encode(System.currentTimeMillis()),
                LocalDateTime.now()
            )
        `when`(urlShortenerRepository.findByHash(anyString())).thenReturn(urlMetadata)
        val shortenedUrlRequest = ShortenedUrlRequest(url)
        var response = urlShortenerService.convertToShortUrl(shortenedUrlRequest, baseUrl)
        verify(urlShortenerRepository, times(0)).save(any())
        assertResponse(response)
    }

    @Test
    fun shouldGetRedirectUrl() {

        val urlMetadata =
            UrlMetadata(
                0L,
                UrlHasher.hashString(url),
                url,
                UrlEncoderDecoder.encode(System.currentTimeMillis()),
                LocalDateTime.now()
            )
        `when`(urlShortenerRepository.findByShortUrl(anyString())).thenReturn(urlMetadata)
        val redirectUrl = urlShortenerService.getActualRedirectUrl("DXSD")
        assertThat(redirectUrl).isNotNull
    }

    @Test
    fun shouldThrowEntityNotFound() {

        assertThrows<EntityNotFoundException> {
            urlShortenerService.getActualRedirectUrl("DXSD")
        }
    }

    private fun assertResponse(response: ShortenedUrlResponse) {

        assertThat(response).isNotNull
        assertThat(response.requestedUrl).isEqualTo(url)
        assertThat(response.alias).isNotNull
        assertThat(response.miniurl).isEqualTo(baseUrl + UrlShortenerApi.UrlConstant.API_PATH + "/" + response.alias)
        assertThat(response.createdAt).isNotNull

    }

}