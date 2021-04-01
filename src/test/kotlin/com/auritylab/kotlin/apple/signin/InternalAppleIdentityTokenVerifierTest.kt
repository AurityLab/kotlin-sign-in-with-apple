package com.auritylab.kotlin.apple.signin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class InternalAppleIdentityTokenVerifierTest {
    @Test
    fun `should verify token correctly`() {
        val testKey = TestUtils.buildTestKey()
        val testJWT = TestUtils.buildTestJWT(testKey)

        val verifier = InternalAppleIdentityTokenVerifier()

        val result = verifier.isValid(testJWT, mapOf("1" to testKey.first), "com.auritylab.test")

        Assertions.assertTrue(result)
    }

    @Test
    fun `should throw exception on invalid token correctly`() {
        val testKey = TestUtils.buildTestKey()
        val testJWT = TestUtils.buildTestJWT(testKey)

        val verifier = InternalAppleIdentityTokenVerifier()

        val result = verifier.isValid(testJWT, mapOf("1" to testKey.first), "NO")

        Assertions.assertFalse(result)
    }
}
