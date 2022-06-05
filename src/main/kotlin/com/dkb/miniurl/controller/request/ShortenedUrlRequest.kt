package com.dkb.miniurl.controller.request

import com.dkb.miniurl.controller.validator.ValidUrl

data class ShortenedUrlRequest(@ValidUrl var url: String)
