package com.dkb.miniurl.mapper

import com.dkb.miniurl.business.entities.UrlMetadata
import com.dkb.miniurl.controller.UrlShortenerApi
import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import com.dkb.miniurl.util.UrlEncoderDecoder
import com.dkb.miniurl.util.UrlHasher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import java.time.LocalDateTime


class UrlMapperTest {

    val urlMapper = Mappers.getMapper(UrlMapper::class.java)
    val url = "http://testmapper.com"

    @Test
    fun shouldMapShortenedUrlRequestToUrlMetadata() {

        val shortenedUrlRequest = ShortenedUrlRequest(url)
        val urlMetadata = urlMapper.toUrlMetaData(shortenedUrlRequest)

        assertThat(urlMetadata).isNotNull
        assertThat(urlMetadata.hash).isNotNull
        assertThat(urlMetadata.hash).isEqualTo(UrlHasher.hashString(url))
        assertThat(urlMetadata.shortUrl).isNotNull

    }

    @Test
    fun shouldMapUrlMetadataToShortUrlResponse() {
        val baseUrl = "http://baseurl.com/"
        val urlMetadata =
            UrlMetadata(
                0L,
                UrlHasher.hashString(url),
                url,
                UrlEncoderDecoder.encode(System.currentTimeMillis()),
                LocalDateTime.now()
            )

        val response = urlMapper.toShortenedUrlResponse(urlMetadata, baseUrl)
        assertThat(response).isNotNull
        assertThat(response.requestedUrl).isEqualTo(url)
        assertThat(response.alias).isNotNull
        assertThat(response.miniurl).isEqualTo(baseUrl + UrlShortenerApi.UrlConstant.API_PATH+"/" + response.alias)
        assertThat(response.createdAt).isNotNull

    }

}