package com.dkb.miniurl.util

import java.security.MessageDigest

object UrlHasher {
    /**
     * The method uses SHA-256 algorithm to convert the input Url string to hash
     * @param input Url
     * @return hash of the input string
     */
    fun hashString(input: String): String {

        return MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
}
