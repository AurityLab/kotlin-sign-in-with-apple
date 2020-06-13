package com.auritylab.kotlin.apple.signin.exception

/**
 * Represents an exception which will be thrown when the public key could not be fetched from Apple.
 */
class ApplePublicKeyResolveException(cause: Throwable) :
        AppleSignInException("Unable to fetch the public key from Apple", cause)
