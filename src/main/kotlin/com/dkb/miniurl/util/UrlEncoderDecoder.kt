package com.dkb.miniurl.util

object UrlEncoderDecoder {

    private val allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val allowedCharacters = allowedString.toCharArray()
    private val base = allowedCharacters.size

    /**
     * The method returns the url alias based on input
     * @param input , long number based on which encoded string is returned
     * @return the encoded String
     */
    fun encode(input: Long): String {
        var input = input
        val encodedString = StringBuilder()
        if (input == 0L) {
            return allowedCharacters[0].toString()
        }
        while (input > 0) {
            encodedString.append(allowedCharacters[(input % base).toInt()])
            input = input / base
        }
        return encodedString.reverse().toString()
    }

    private fun decode(input: String): Long {
        val characters = input.toCharArray()
        val length = characters.size
        var decoded = 0

        //counter is used to avoid reversing input string
        var counter = 1
        for (i in 0 until length) {
            decoded += (allowedString.indexOf(characters[i]) * Math.pow(
                base.toDouble(),
                (length - counter).toDouble()
            )).toInt()
            counter++
        }
        return decoded.toLong()
    }
}