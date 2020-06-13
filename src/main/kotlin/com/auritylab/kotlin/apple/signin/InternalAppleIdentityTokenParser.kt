package com.auritylab.kotlin.apple.signin

import com.auritylab.kotlin.apple.signin.api.AppleIdentityTokenParser
import com.auritylab.kotlin.apple.signin.exception.AppleIdentityTokenDecodeException
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import java.time.LocalDateTime
import java.time.ZoneId

internal class InternalAppleIdentityTokenParser : AppleIdentityTokenParser {
    override fun parseIdentityToken(identityToken: String): AppleIdentityToken {
        val decodedIdentityToken = try {
            JWT.decode(identityToken)
        } catch (ex: JWTDecodeException) {
            throw AppleIdentityTokenDecodeException(ex)
        }

        return AppleIdentityToken(
                decodedIdentityToken.getClaim("sub").asString(),
                decodedIdentityToken.getClaim("email").asString(),
                decodedIdentityToken.getClaim("email_verified").asString()
                        ?.let { it == "true" } ?: true,
                LocalDateTime.ofInstant(
                        decodedIdentityToken.issuedAt.toInstant(),
                        ZoneId.of("UTC")
                )
        )
    }
}
