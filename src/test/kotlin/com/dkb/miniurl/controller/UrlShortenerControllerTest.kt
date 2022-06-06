package com.dkb.miniurl.controller

import com.dkb.miniurl.business.services.UrlShortenerService
import com.dkb.miniurl.controller.response.ShortenedUrlResponse
import com.dkb.miniurl.fixture.UrlShortenerFixture.URI
import com.dkb.miniurl.fixture.UrlShortenerFixture.createShortenedUrlRequest
import com.dkb.miniurl.fixture.UrlShortenerFixture.invalidShortenedUrlRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.time.ZonedDateTime
import javax.persistence.EntityNotFoundException

@WebMvcTest(controllers = [UrlShortenerController::class])
class UrlShortenerControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @MockBean
    private lateinit var urlShortenerService: UrlShortenerService

    @DisplayName("The test call createShortenedUrl to create a miniurl")
    @Test
    fun shouldCreateShortUrl() {

        val builder = MockMvcRequestBuilders.post(URI)
            .content(createShortenedUrlRequest)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        val response = mockMvc.perform(builder).andReturn()
        assertThat(response.response.status).isEqualTo(HttpStatus.OK.value())
    }

    @DisplayName("The test return BAD_REQUEST in invalid input")
    @Test
    fun shouldReturnErrorOnInvalidInputUrl() {

        val builder = MockMvcRequestBuilders.post(URI)
            .content(invalidShortenedUrlRequest)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        val response = mockMvc.perform(builder).andReturn()
        assertThat(response.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @DisplayName("The test should return FOUND(302) for valid redirect")
    @Test
    fun shouldRedirectToActualUrl() {

        val urlResponse = ShortenedUrlResponse("","","",ZonedDateTime.now(),"")
        `when`(urlShortenerService.getActualRedirectUrl(anyString())).thenReturn(urlResponse)

        val builder = MockMvcRequestBuilders.get(URI+"/shortcode")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        val response = mockMvc.perform(builder).andReturn()
        assertThat(response.response.status).isEqualTo(HttpStatus.FOUND.value())
    }

    @DisplayName("The test should return NOT_FOUND(404) for valid redirect")
    @Test
    fun shouldReturn404RedirectToActualUrl() {

        `when`(urlShortenerService.getActualRedirectUrl(anyString())).thenThrow(
            EntityNotFoundException()
        )

        val builder = MockMvcRequestBuilders.get(URI+"/shortcode")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        val response = mockMvc.perform(builder).andReturn()
        assertThat(response.response.status).isEqualTo(HttpStatus.NOT_FOUND.value())
    }



}