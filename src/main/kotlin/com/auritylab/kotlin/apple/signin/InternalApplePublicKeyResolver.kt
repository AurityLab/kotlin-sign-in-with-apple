package com.auritylab.kotlin.apple.signin

import com.auritylab.kotlin.apple.signin.api.ApplePublicKeyResolver
import com.auritylab.kotlin.apple.signin.exception.ApplePublicKeyResolveException
import java.math.BigInteger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.Base64
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

internal class InternalApplePublicKeyResolver : ApplePublicKeyResolver {
    /**
     * Will fetch the current public keys from the Apple servers and build a [RSAPublicKey]. The result of this may
     * not change often, therefore it can be cached for a certain time.
     */
    override fun getPublicKey(): RSAPublicKey {
        val client = HttpClient.newHttpClient()

        // Build the request to the keys endpoint.
        val request = HttpRequest.newBuilder().GET().uri(URI.create(KEYS_ENDPOINT)).build()

        try {
            // Execute the request and parse the response
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())

            // Parse the keys into the models.
            val decoded = Json(JsonConfiguration.Stable).parse(KeysCollectionModel.serializer(), response.body())

            // Build the public key based on the public key.
            return buildPublicKey(decoded.keys.first())
        } catch (ex: Exception) {
            throw ApplePublicKeyResolveException(ex)
        }
    }

    /**
     * Will build the [RSAPublicKey] based on the given [KeyModel].
     */
    private fun buildPublicKey(key: KeyModel): RSAPublicKey {
        val n = BigInteger(1, Base64.getUrlDecoder().decode(key.n))
        val e = BigInteger(1, Base64.getUrlDecoder().decode(key.e))

        return KeyFactory.getInstance("RSA")
                .generatePublic(RSAPublicKeySpec(n, e)) as RSAPublicKey
    }

    @Serializable
    internal data class KeysCollectionModel(
        val keys: List<KeyModel>
    )

    @Serializable
    internal data class KeyModel(
        val kty: String,
        val kid: String,
        val use: String,
        val alg: String,
        val n: String,
        val e: String
    )

    companion object {
        /**
         * The endpoint from which we can obtain the public key.
         */
        private const val KEYS_ENDPOINT = "https://appleid.apple.com/auth/keys"
    }
}
