package com.dkb.miniurl.mapper

import com.dkb.miniurl.business.entities.UrlMetadata
import com.dkb.miniurl.controller.UrlShortenerApi
import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import com.dkb.miniurl.controller.response.ShortenedUrlResponse
import com.dkb.miniurl.util.DateUtils
import com.dkb.miniurl.util.UrlEncoderDecoder
import com.dkb.miniurl.util.UrlHasher
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import java.nio.charset.StandardCharsets

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = [UrlHasher::class, StandardCharsets::class, DateUtils::class, UrlEncoderDecoder::class, UrlShortenerApi::class]
)
interface UrlMapper {
    /**
     * The method Maps the [ShortenedUrlRequest] to  [UrlMetadata] entity
     * @param shortenedUrlRequest input request
     * @return the mapped [UrlMetadata]
     */
    @Mapping(target = "longUrl", source = "url")
    @Mapping(
        target = "shortUrl",
        expression = "java(UrlEncoderDecoder.INSTANCE.encode(System.currentTimeMillis()))"
    )
    @Mapping(
        target = "hash",
        expression = "java(new String(UrlHasher.INSTANCE.hashString(shortenedUrlRequest.getUrl())))"
    )
    fun toUrlMetaData(shortenedUrlRequest: ShortenedUrlRequest): UrlMetadata

    /**
     * The method maps [UrlMetadata] to [ShortenedUrlResponse]
     * @param [UrlMetadata] entity
     * @param baseUrl, the base request Url
     * @return [ShortenedUrlResponse]
     */
    @Mapping(target = "requestedUrl", source = "urlMetadata.longUrl")
    @Mapping(target = "alias", source = "urlMetadata.shortUrl")
    @Mapping(
        target = "createdAt",
        expression = "java(DateUtils.INSTANCE.toZonedDateTime(urlMetadata.getCreationTimestamp()))"
    )
    @Mapping(
        target = "miniurl",
        expression = "java(baseUrl+UrlShortenerApi.UrlConstant.API_PATH+\"/\"+urlMetadata.getShortUrl())"
    )
    fun toShortenedUrlResponse(urlMetadata: UrlMetadata, baseUrl: String): ShortenedUrlResponse
}