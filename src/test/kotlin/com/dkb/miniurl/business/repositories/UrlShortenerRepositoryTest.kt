package com.dkb.miniurl.business.repositories

import com.dkb.miniurl.business.entities.UrlMetadata
import com.dkb.miniurl.config.JpaAuditingConfiguration
import com.dkb.miniurl.util.UrlEncoderDecoder
import com.dkb.miniurl.util.UrlHasher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaAuditingConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UrlShortenerRepositoryTest(@Autowired var urlShortenerRepository: UrlShortenerRepository) {


    val url = "http://test.com"

    @Test
    @DisplayName("The test finds the record by the hash provided")
    fun shouldFindRecordByHash() {


        val hash = UrlHasher.hashString(url)

        setupUrlMetaData(url, hash)
        val fetchedUrlMetadata = urlShortenerRepository.findByHash(hash);
        assertUrlMetadata(fetchedUrlMetadata)
    }

    @Test
    @DisplayName("The test finds the record by the shortUrl provided")
    fun shouldFindRecordByShortUrl() {

        val hash = UrlHasher.hashString(url)
        val persistedMetadata = setupUrlMetaData(url, hash)

        val fetchedUrlMetadata = urlShortenerRepository.findByShortUrl(persistedMetadata.shortUrl)
        assertUrlMetadata(fetchedUrlMetadata)
    }

    @DisplayName("The test checks if null is returned when the hash is not found")
    @Test
    fun shouldRetrunNullWhenNoHashIsFound() {
        val fetchedUrlMetadata = urlShortenerRepository.findByHash("DUMMY HASH");
        assertThat(fetchedUrlMetadata).isNull()
    }

    @DisplayName("The test checks if null is returned when the shorturl is not found")
    @Test
    fun shouldRetrunNullWhenNoShorturlIsFound() {
        val fetchedUrlMetadata = urlShortenerRepository.findByShortUrl("DUMMY SHORTURL");
        assertThat(fetchedUrlMetadata).isNull()
    }


    @Nested
    inner class ConstraintValidations {

        @Test
        fun shouldThrowExceptionForDuplicateHashField() {
            assertThrows<DataIntegrityViolationException> {
                val urlMetadata =
                    UrlMetadata(
                        0L,
                        "DUMMY HASH",
                        url,
                        UrlEncoderDecoder.encode(System.currentTimeMillis())
                    )
                val urlMetadataDuplicate =
                    UrlMetadata(
                        0L,
                        "DUMMY HASH",
                        url,
                        UrlEncoderDecoder.encode(System.currentTimeMillis())
                    )
                urlShortenerRepository.saveAllAndFlush(listOf(urlMetadata, urlMetadataDuplicate));
            }
        }

    }

    private fun setupUrlMetaData(url: String, hash: String): UrlMetadata {

        val urlMetadata =
            UrlMetadata(0L, hash, url, UrlEncoderDecoder.encode(System.currentTimeMillis()))
        return urlShortenerRepository.save(urlMetadata);

    }

    private fun assertUrlMetadata(urlMetadata: UrlMetadata?) {

        assertThat(urlMetadata).isNotNull()
        assertThat(urlMetadata?.id).isNotNull()
        assertThat(urlMetadata?.hash).isNotNull()
        assertThat(urlMetadata?.hash).isEqualTo(UrlHasher.hashString(url))
        assertThat(urlMetadata?.shortUrl).isNotNull()
        assertThat(urlMetadata?.longUrl).isEqualTo(url)
        assertThat(urlMetadata?.creationTimestamp).isNotNull()

    }
}