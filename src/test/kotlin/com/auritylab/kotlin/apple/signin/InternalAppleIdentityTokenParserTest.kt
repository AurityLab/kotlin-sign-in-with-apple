package com.auritylab.kotlin.apple.signin

import com.auritylab.kotlin.apple.signin.exception.AppleIdentityTokenDecodeException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class InternalAppleIdentityTokenParserTest {
    companion object {
        // An test token which is self signed.
        private const val TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLmF1cml0eWxhYi50ZXN0IiwiZXhwIjoxNTkyMDU1NTg4LCJpYXQiOjE1OTIwMzczODksInN1YiI6IjEyMzQiLCJjX2hhc2giOiJobVd0bzZCUDFsaDloWk1vc0NsdGdnIiwiZW1haWwiOiJ0ZXN0QGF1cml0eWxhYi5jb20iLCJlbWFpbF92ZXJpZmllZCI6InRydWUiLCJhdXRoX3RpbWUiOjE1OTIwMzczODksIm5vbmNlX3N1cHBvcnRlZCI6dHJ1ZSwianRpIjoiYWFlNWI5NjYtMTc5Yy00ODRlLWI4NWItZGM5NjFlM2UyNTNiIn0.M-6LhGYpgfwRxy1T-uGQc8YWsYdTJ89P3YbHPixN4_A"
    }

    @Test
    fun `should parse the token into the model correctly`() {
        val parser = InternalAppleIdentityTokenParser()

        val response = parser.parseIdentityToken(TEST_TOKEN)

        Assertions.assertEquals("1234", response.userIdentifier)
        Assertions.assertEquals("test@auritylab.com", response.email)
        Assertions.assertTrue(response.emailVerified)
    }

    @Test
    fun `should throw on invalid token correctly` () {
        val parser = InternalAppleIdentityTokenParser()

        Assertions.assertThrows(AppleIdentityTokenDecodeException::class.java) {
            parser.parseIdentityToken("no jwt token at all")
        }
    }
}
