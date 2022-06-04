package com.dkb.miniurl.util

import java.time.LocalDateTime

class DateUtils {

    companion object {

        fun addDaysToTs(daysToAdd: Long, inputTimestamp: LocalDateTime): LocalDateTime {
            return LocalDateTime.now().plusDays(daysToAdd);
        }

    }
}