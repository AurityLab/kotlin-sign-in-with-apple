package com.auritylab.kotlin.apple.signin.exception

/**
 * Represents an exception which will be thrown when the identity token could not be decoded.
 */
class AppleIdentityTokenDecodeException(cause: Throwable?) :
        AppleSignInException("Identity token could not be decoded", cause)
