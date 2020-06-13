package com.auritylab.kotlin.apple.signin.api

import com.auritylab.kotlin.apple.signin.AppleIdentityToken
import com.auritylab.kotlin.apple.signin.exception.AppleIdentityTokenDecodeException

/**
 * Describes a parser the for identity token.
 */
interface AppleIdentityTokenParser {
    /**
     * Will parse the given [identityToken] into an [AppleIdentityToken]. If the given [identityToken] could not be
     * parsed, an [AppleIdentityTokenDecodeException] will be thrown.
     *
     * @param identityToken The identity token to parse.
     * @return The [AppleIdentityToken] instance.
     * @throws AppleIdentityTokenDecodeException If the given [identityToken] could not be parsed.
     */
    fun parseIdentityToken(identityToken: String): AppleIdentityToken
}
