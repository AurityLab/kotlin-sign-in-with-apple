package com.auritylab.kotlin.apple.signin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class InternalApplePublicKeyResolverTest {
    @Test
    fun `should fetch public key correctly`() {
        val resolver = InternalApplePublicKeyResolver()

        val response = resolver.getPublicKey()

        // Just assert against null as it will fail anyways if there is a HTTP exception.
        Assertions.assertNotNull(response)
    }
}
