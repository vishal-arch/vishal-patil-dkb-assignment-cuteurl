package com.dkb.miniurl.config

import com.dkb.miniurl.controller.request.ShortenedUrlRequest
import com.dkb.miniurl.util.UrlHasher
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Method
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 * The class represent the custom cache Configuration
 */
@Configuration
@EnableCaching
class CachingConfiguration {

    @Bean
    fun customKeyGenerator(): KeyGenerator {
        return CustomKeyGenerator()
    }


    class CustomKeyGenerator : KeyGenerator {
        /**
         * The metod is used to generate custom based on the hash
         */
        override fun generate(target: Any, method: Method, vararg params: Any?): Any {
            if (params[0] is ShortenedUrlRequest) {
                val req = params[0] as ShortenedUrlRequest
                val decodedUrl = URLDecoder.decode(req.url, StandardCharsets.UTF_8);
                return UrlHasher.hashString(decodedUrl)
            }
            throw IllegalArgumentException("Key cannot be generated")
        }
    }
}