package com.dkb.miniurl.fixture

object UrlShortenerFixture {

    val URI = "/v1/api/short"

    val createShortenedUrlRequest = """
        {
           "url": "http://www.someurl.com"
        } 
    """

    val invalidShortenedUrlRequest = """
        {
           "url": "someurlcom"
        }
         """

}