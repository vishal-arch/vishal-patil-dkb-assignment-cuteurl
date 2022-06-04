package com.dkb.miniurl.util

import java.security.MessageDigest

class UrlHasher {

    companion object {
        fun hashString(input: String): String {
            return MessageDigest
                .getInstance("SHA-256")
                .digest(input.toByteArray())
                .fold("", { str, it -> str + "%02x".format(it) })
        }
    }
}