package com.dkb.miniurl.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UrlEncoderDecoderTest {

    @Test
    fun shouldEncodeUrl(){

        assertThat(UrlEncoderDecoder.encode(100L)).isNotNull

    }
}