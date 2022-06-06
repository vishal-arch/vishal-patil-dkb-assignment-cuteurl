package com.dkb.miniurl.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UrlHasherTest {

    @Test
    fun shouldHashUrl(){
        assertThat(UrlHasher.hashString("http://dummyurl.com")).isNotNull
    }
}