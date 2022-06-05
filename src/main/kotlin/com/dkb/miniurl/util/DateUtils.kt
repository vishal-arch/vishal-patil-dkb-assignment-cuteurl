package com.dkb.miniurl.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

object DateUtils {
    /**
     * The method converts localDateTime to ZonedDateTime
     * @param LocalDateTime passed as an input
     * @return ZonedDateTime with System ZoneId
     */
    fun toZonedDateTime(localDateTime: LocalDateTime): ZonedDateTime {
        return localDateTime.atZone(ZoneId.systemDefault())
    }
}