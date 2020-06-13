package com.auritylab.kotlin.apple.signin

import com.auritylab.kotlin.apple.signin.exception.AppleIdentityTokenDecodeException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class AppleSignInTest {
    @Test
    fun `should validate valid identity token correctly`() {
        val testKey = TestUtils.buildTestKey()
        val testKeyResolver = TestUtils.buildMockedApplePublicKeyResolver(testKey)

        val appleSignIn = AppleSignIn("com.auritylab.test", testKeyResolver)

        val result = appleSignIn.validate(TestUtils.buildTestJWT(testKey))

        Assertions.assertNotNull(result)

        result!!
        Assertions.assertEquals("test@auritylab.com", result.email)
        Assertions.assertEquals("1234", result.userIdentifier)
    }

    @Test
    fun `should throw exception on invalid identity token correctly`() {
        val testKey = TestUtils.buildTestKey()
        val testKeyResolver = TestUtils.buildMockedApplePublicKeyResolver(testKey)

        val appleSignIn = AppleSignIn("com.auritylab.test", testKeyResolver)

        Assertions.assertThrows(AppleIdentityTokenDecodeException::class.java) {
            appleSignIn.validate("NO JWT TOKEN AT ALL")
        }
    }

    @Test
    fun `should return null-reference on invalid token correctly`() {
        val testKey = TestUtils.buildTestKey()
        val testKeyResolver = TestUtils.buildMockedApplePublicKeyResolver(testKey)

        val appleSignIn = AppleSignIn("com.auritylab.test", testKeyResolver)

        Assertions.assertNull(appleSignIn.validate(TestUtils.buildTestJWT(testKey, true)))
    }
}
