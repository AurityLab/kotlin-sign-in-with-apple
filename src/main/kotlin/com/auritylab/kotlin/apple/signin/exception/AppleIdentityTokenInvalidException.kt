package com.auritylab.kotlin.apple.signin.exception

/**
 * Represents an exception which will be thrown if an identity token is invalid. This might be due to multiple reasons.
 */
class AppleIdentityTokenInvalidException(message: String) : AppleSignInException(message, null)
