package com.auritylab.kotlin.apple.signin.api

import com.auritylab.kotlin.apple.signin.exception.ApplePublicKeyResolveException
import java.security.interfaces.RSAPublicKey

/**
 * Describes a resolver for the public key provided by the Apple servers.
 */
interface ApplePublicKeyResolver {
    /**
     * Will return all available public keys as [RSAPublicKey] instances. Each public key is mapped by its 'kid'.
     * If the public key could not be loaded, an [ApplePublicKeyResolveException] will be thrown.
     *
     * @throws ApplePublicKeyResolveException If the public key could not be fetched.
     * @return The fetched public keys mapped with their 'kid'.
     */
    fun getPublicKey(): Map<String, RSAPublicKey>
}
