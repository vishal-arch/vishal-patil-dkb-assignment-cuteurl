package com.dkb.miniurl.config

import com.dkb.miniurl.controller.response.ShortenedUrlResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import java.time.Duration


@Configuration
class RedisConfiguration {

    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration {

        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        mapper.registerModule(kotlinModule())
        val serializer = Jackson2JsonRedisSerializer(ShortenedUrlResponse::class.java)
        serializer.setObjectMapper(mapper)

        return RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues()
            .entryTtl(Duration.ofMinutes(60))
            .serializeValuesWith(SerializationPair.fromSerializer(serializer))
    }

}