package com.dkb.miniurl.controller.response

import java.time.ZonedDateTime

data class ShortenedUrlResponse(
    val requestedUrl: String,
    val hash: String,
    val alias: String,
    val createdAt: ZonedDateTime,
    val miniurl: String
)