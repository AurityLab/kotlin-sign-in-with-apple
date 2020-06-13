package com.auritylab.kotlin.apple.signin

import java.time.LocalDateTime

/**
 * Represents the data which is being hold by the identity token. This only includes required information.
 */
data class AppleIdentityToken(
        /**
         * The unique identifier for the user.
         */
        val userIdentifier: String,
        /**
         * The email address of the user.
         */
        val email: String,
        /**
         * A Boolean value that indicates whether the service has verified the email.
         * The value of this claim is always true because the servers only return verified email addresses.
         */
        val emailVerified: Boolean,
        /**
         * The time the token was issued.
         */
        val issuedAt: LocalDateTime
)
