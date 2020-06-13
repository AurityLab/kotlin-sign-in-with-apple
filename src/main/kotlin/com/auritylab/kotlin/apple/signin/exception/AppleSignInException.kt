package com.auritylab.kotlin.apple.signin.exception

/**
 * Represents the base exception for all exceptions which are involved in Apple Sign In.
 */
open class AppleSignInException(message: String?, cause: Throwable?) : RuntimeException(message, cause)
