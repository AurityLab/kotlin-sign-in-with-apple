package com.auritylab.kotlin.apple.signin

import com.auritylab.kotlin.apple.signin.api.AppleIdentityTokenVerifier
import com.auritylab.kotlin.apple.signin.exception.AppleIdentityTokenDecodeException
import com.auritylab.kotlin.apple.signin.exception.AppleIdentityTokenInvalidException
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.JWTVerifier
import java.security.interfaces.RSAPublicKey

/**
 * Represents a helper to create a [JWTVerifier] and verify an identity token.
 */
internal class InternalAppleIdentityTokenVerifier : AppleIdentityTokenVerifier {
    /**
     * Will check if the given [identityToken] is valid for the given [publicKeys] and [clientId].
     * This catches all underlying exceptions by the JWT library and simply return false on failure.
     *
     * @param identityToken The identity token to validate against.
     * @param publicKeys The public key provided by the Apple servers.
     * @param clientId The client ID of the developer.
     * @return If the given [identityToken] is valid or not.
     */
    override fun isValid(identityToken: String, publicKeys: Map<String, RSAPublicKey>, clientId: String): Boolean {
        // Load the 'kid' claim of the header.
        val kidOfIdentityToken = getKid(identityToken)

        // Load the public key by the given 'kid'. If no valid kid is available, just return false as we can't verify at all.
        val possiblePublicKey = publicKeys[kidOfIdentityToken] ?: return false

        // Build the the verifier based on the public key and the developer client id.
        val verifier = getJWTVerifier(possiblePublicKey, clientId)

        return try {
            // Try to validate the identity token. This always just throw an exception of the verification fails.
            verifier.verify(identityToken)
            true
        } catch (ex: JWTDecodeException) {
            // Throw the decode exception, as the token is not valid at all.
            throw AppleIdentityTokenDecodeException(ex)
        } catch (ex: JWTVerificationException) {
            // Catch all verification exceptions and just return false.
            false
        }
    }

    /**
     * Will build a [JWTVerifier] based on the given [publicKey] and the given [clientId].
     * This will validate the signature, issuer and audience of token.
     */
    private fun getJWTVerifier(publicKey: RSAPublicKey, clientId: String): JWTVerifier {
        return JWT.require(Algorithm.RSA256(publicKey, null))
            .withIssuer("https://appleid.apple.com")
            .withAudience(clientId)
            .build()
    }

    /**
     * Will resolve the 'kid' claim of the given [identityToken]. If the 'kid' claim is not set,
     * an [AppleIdentityTokenInvalidException] will be thrown. If the given [identityToken] can not be decoded at all,
     * an [AppleIdentityTokenDecodeException] will be thrown.
     *
     * @throws AppleIdentityTokenInvalidException If the header of the identity token does not contain the 'kid' claim.
     * @throws AppleIdentityTokenDecodeException If the identity token could not be decoded.
     */
    private fun getKid(identityToken: String): String {
        try {
            val kidClaim = JWT.decode(identityToken).getHeaderClaim("kid")
                ?: throw AppleIdentityTokenInvalidException("Header of identity token does not contain 'kid' claim")

            return kidClaim.asString()
                ?: throw AppleIdentityTokenInvalidException("Header of identity token does not contain a valid 'kid' claim")
        } catch (ex: JWTDecodeException) {
            throw AppleIdentityTokenDecodeException(ex)
        }
    }
}
