package com.auritylab.kotlin.apple.signin.api

import com.auritylab.kotlin.apple.signin.exception.AppleIdentityTokenDecodeException
import java.security.interfaces.RSAPublicKey

/**
 * Describes a verifier for the identity token.
 */
interface AppleIdentityTokenVerifier {
    /**
     * Will verify the given [identityToken] based on the given [publicKey] and the [developerClientId].
     * If the verification fails, this will just return false. But if the given identity token could not even be
     * decoded, an [AppleIdentityTokenDecodeException] will be thrown.
     *
     * @param identityToken The identity token which shall be verified.
     * @param publicKey The public key to verify the signature against.
     * @param developerClientId The client ID of the developer.
     * @return If the given [identityToken] is valid.
     * @throws AppleIdentityTokenDecodeException If the [identityToken] could not be decoded.
     */
    fun isValid(identityToken: String, publicKey: RSAPublicKey, developerClientId: String): Boolean
}
