package com.dkb.miniurl.mapper

import com.dkb.miniurl.business.entities.UrlMetadata
import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import com.dkb.miniurl.util.UrlHasher
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.nio.charset.StandardCharsets

@Mapper(imports = [UrlHasher::class, StandardCharsets::class])
interface UrlMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "longUrl", source = "url")
    @Mapping(target = "shortUrl", ignore = true)
    @Mapping(
        target = "hash",
        expression = "java(new String(UrlHasher.Companion.hashString(shortenedUrlRequest.getUrl())))"
    )
    @Mapping(target = "expirationTimestamp", ignore = true)
    @Mapping(target = "creationTimestamp", ignore = true)
    @Mapping(target = "updationTimestamp", ignore = true)
    fun toUrlMetaData(shortenedUrlRequest: ShortenedUrlRequest): UrlMetadata


}