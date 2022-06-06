package com.dkb.miniurl.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class DateUtilTest {

    @Test
    fun shouldMapLocalDateToZoneDateTime(){
      val zoneDateTime = DateUtils.toZonedDateTime(LocalDateTime.now())
      assertThat(zoneDateTime).isNotNull
    }
}