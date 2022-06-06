package com.dkb.miniurl.integration

import com.dkb.miniurl.business.entities.UrlMetadata
import com.dkb.miniurl.business.repositories.UrlShortenerRepository
import com.dkb.miniurl.controller.response.ShortenedUrlResponse
import com.dkb.miniurl.fixture.UrlShortenerFixture.URI
import com.dkb.miniurl.fixture.UrlShortenerFixture.createShortenedUrlRequest
import com.dkb.miniurl.fixture.UrlShortenerFixture.invalidShortenedUrlRequest
import com.dkb.miniurl.util.UrlEncoderDecoder
import com.dkb.miniurl.util.UrlHasher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import java.net.URI


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class IntegrationUrlShortenerControllerTest(
    @Autowired
    val repository: UrlShortenerRepository,
    @Autowired
    val restTemplate: TestRestTemplate
) {

    val url = "http://www.someurl.com"

    @Test
    fun shouldReturn200OnCreationOfShortUrl() {

        val httpEntity = HttpEntity(createShortenedUrlRequest, getHttpHeaders())
        var response =
            restTemplate.postForEntity(URI(URI), httpEntity, ShortenedUrlResponse::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        var urlMetadata = repository.findAll();
        assertThat(urlMetadata.size).isEqualTo(1)
        assertUrlMetadata(urlMetadata.get(0))
    }

    @Test
    fun shouldReturn400OnBadInputRequest() {

        val httpEntity = HttpEntity(invalidShortenedUrlRequest, getHttpHeaders())
        var response = restTemplate.postForEntity(URI(URI), httpEntity, String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun shouldReturn302ForUrlRedirection() {
        val persistedUrlMetadata = setupUrlMetaData(url, UrlHasher.hashString(url))
        val response = restTemplate.getForEntity(
            URI + "/" + persistedUrlMetadata.shortUrl,
            ShortenedUrlResponse::class.java
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.FOUND)
    }

    @Test
    fun shouldReturn404WhenShortCodeNotFound() {
        val response = restTemplate.getForEntity(URI + "/dummy_value", String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    private fun getHttpHeaders(): HttpHeaders {
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_JSON
        return header
    }

    private fun setupUrlMetaData(url: String, hash: String): UrlMetadata {

        val urlMetadata =
            UrlMetadata(0L, hash, url, UrlEncoderDecoder.encode(System.currentTimeMillis()))
        return repository.save(urlMetadata);

    }

    private fun assertUrlMetadata(urlMetadata: UrlMetadata?) {

        assertThat(urlMetadata).isNotNull
        assertThat(urlMetadata?.id).isNotNull
        assertThat(urlMetadata?.hash).isNotNull
        assertThat(urlMetadata?.hash).isEqualTo(UrlHasher.hashString(url))
        assertThat(urlMetadata?.shortUrl).isNotNull
        assertThat(urlMetadata?.longUrl).isEqualTo(url)
        assertThat(urlMetadata?.creationTimestamp).isNotNull

    }
}