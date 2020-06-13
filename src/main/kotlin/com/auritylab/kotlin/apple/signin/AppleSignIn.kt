package com.auritylab.kotlin.apple.signin

import com.auritylab.kotlin.apple.signin.api.AppleIdentityTokenParser
import com.auritylab.kotlin.apple.signin.api.AppleIdentityTokenVerifier
import com.auritylab.kotlin.apple.signin.api.ApplePublicKeyResolver

/**
 * Provides central facade to interact with the verification process of an identity token provided by Apple Sign In.
 * This will take care about fetching the public keys provided by the Apple servers and verifying the signature and
 * the claims of the identity token.
 */
class AppleSignIn(
    private val developerClientId: String,
    publicKeyResolver: ApplePublicKeyResolver? = null,
    identityTokenParser: AppleIdentityTokenParser? = null,
    identityTokenVerifier: AppleIdentityTokenVerifier? = null
) {
    private val internalPublicKeyResolver = publicKeyResolver ?: InternalApplePublicKeyResolver()
    private val internalIdentityTokenParser = identityTokenParser ?: InternalAppleIdentityTokenParser()
    private val internalIdentityTokenVerifier = identityTokenVerifier ?: InternalAppleIdentityTokenVerifier()

    /**
     * Secondary constructor for easier construction with default implementations.
     */
    constructor(developerClientId: String) : this(developerClientId, null, null)

    /**
     * Will check if the given [identityToken] is valid. If it's not valid, a null-reference will be returned.
     *
     * @param identityToken The identity token to validate against.
     * @return If the given [identityToken] is valid or not.
     */
    fun validate(identityToken: String): AppleIdentityToken? {
        // Validate the identity token.
        val isValid = internalIdentityTokenVerifier.isValid(
            identityToken,
            internalPublicKeyResolver.getPublicKey(),
            developerClientId
        )

        // If the identity token is invalid, we can just return null here.
        if (!isValid)
            return null

        return internalIdentityTokenParser.parseIdentityToken(identityToken)
    }
}
