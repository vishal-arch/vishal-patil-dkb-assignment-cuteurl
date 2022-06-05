package com.dkb.miniurl.business.repositories

import com.dkb.miniurl.business.entities.UrlMetadata
import org.springframework.data.jpa.repository.JpaRepository

/**
 * The repository class provides method related to Persistence of [UrlMetadata] entity.
 * for details please have a look at [UrlMetadata]
 *
 */
interface UrlShortenerRepository : JpaRepository<UrlMetadata, Long> {

    /**
     * The method fetches the UrlMetadata details for the Hash calculated
     * @param hash, This is the hash code calculated from the request Url passed
     * @return null or the [UrlMetadata] found in the DB
     */
    fun findByHash(hash: String): UrlMetadata?

    /**
     * The method fetches the UrlMetadata details for the shortUrl passed
     * @param shortUrl, This is the shortUrl from the request
     * @return null or the [UrlMetadata] found in the DB
     */
    fun findByShortUrl(shortUrl:String) : UrlMetadata?
}