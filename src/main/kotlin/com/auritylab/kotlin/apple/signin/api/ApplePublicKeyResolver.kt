package com.auritylab.kotlin.apple.signin.api

import com.auritylab.kotlin.apple.signin.exception.ApplePublicKeyResolveException
import java.security.interfaces.RSAPublicKey

/**
 * Describes a resolver for the public key provided by the Apple servers.
 */
interface ApplePublicKeyResolver {
    /**
     * Will return the public keys as a [RSAPublicKey] instance. If the public key could not be loaded,
     * an [ApplePublicKeyResolveException] will be thrown.
     *
     * @throws ApplePublicKeyResolveException If the public key could not be fetched.
     * @return The fetched public key as [RSAPublicKey].
     */
    fun getPublicKey(): RSAPublicKey
}
