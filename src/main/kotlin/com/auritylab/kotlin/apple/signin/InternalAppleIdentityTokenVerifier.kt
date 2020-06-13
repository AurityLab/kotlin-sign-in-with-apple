package com.auritylab.kotlin.apple.signin

import com.auritylab.kotlin.apple.signin.api.AppleIdentityTokenVerifier
import com.auritylab.kotlin.apple.signin.exception.AppleIdentityTokenDecodeException
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
     * Will check if the given [identityToken] is valid for the given [publicKey] and [clientId].
     * This catches all underlying exceptions by the JWT library and simply return false on failure.
     *
     * @param identityToken The identity token to validate against.
     * @param publicKey The public key provided by the Apple servers.
     * @param clientId The client ID of the developer.
     * @return If the given [identityToken] is valid or not.
     */
    override fun isValid(identityToken: String, publicKey: RSAPublicKey, clientId: String): Boolean {
        // Build the the verifier based on the public key and the developer client id.
        val verifier = getJWTVerifier(publicKey, clientId)

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
}
