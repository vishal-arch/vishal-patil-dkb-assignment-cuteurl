package com.dkb.miniurl.config

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import net.logstash.logback.decorate.JsonFactoryDecorator

class LogstashJavaTimeModuleDecorator : JsonFactoryDecorator {

    override fun decorate(factory: JsonFactory): JsonFactory {
        val codec = factory.codec as ObjectMapper
        codec.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return factory
    }
}