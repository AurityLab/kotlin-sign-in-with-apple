package com.auritylab.kotlin.apple.signin

import com.auritylab.kotlin.apple.signin.api.ApplePublicKeyResolver
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.LocalDateTime
import java.time.ZoneOffset

object TestUtils {
    fun buildTestKey(): Pair<RSAPublicKey, RSAPrivateKey> {
        val generated = KeyPairGenerator.getInstance("RSA").genKeyPair()

        return Pair(generated.public as RSAPublicKey, generated.private as RSAPrivateKey)
    }

    fun buildTestJWT(key: Pair<RSAPublicKey, RSAPrivateKey>, expired: Boolean = false): String {
        val exp = if (expired)
            LocalDateTime.now().minusDays(30).toEpochSecond(ZoneOffset.UTC)
        else
            LocalDateTime.now().plusDays(30).toEpochSecond(ZoneOffset.UTC)

        return JWT.create()
                .withClaim("iss", "https://appleid.apple.com")
                .withClaim("aud", "com.auritylab.test")
                .withClaim("exp", exp)
                .withClaim("iat", 1592037389)
                .withClaim("sub", "1234")
                .withClaim("email", "test@auritylab.com")
                .withClaim("email_verified", "true")
                .withClaim("auth_time", 1592037389)
                .withClaim("nonce_supported", true)
                .sign(Algorithm.RSA256(key.first, key.second))
    }

    fun buildMockedApplePublicKeyResolver(key: Pair<RSAPublicKey, RSAPrivateKey>): ApplePublicKeyResolver {
        return object : ApplePublicKeyResolver {
            override fun getPublicKey(): RSAPublicKey = key.first
        }
    }
}
