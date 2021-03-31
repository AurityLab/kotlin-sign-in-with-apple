# Kotlin Sign in with Apple
![GitHub Actions](https://github.com/AurityLab/kotlin-sign-in-with-apple/workflows/Gradle/badge.svg)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
![Maven Central](https://img.shields.io/maven-central/v/com.auritylab/kotlin-sign-in-with-apple?label=kotlin-sign-in-with-apple)


This library is capable of verifying the identity token from Sign in with Apple.

## Install
#### Gradle
```kotlin
dependencies {
    implementation("com.auritylab:kotlin-sign-in-with-apple:1.0.1") // See above for the latest version!
}
```
#### Maven
```xml
<dependency>
    <groupId>com.auritylab</groupId>
    <artifactId>kotlin-sign-in-with-apple</artifactId>
    <version>1.0.1</version> <!-- See above for the latest versions! -->
</dependency>
```


## Usage
```kotlin
// Validate the identity token.
val result = AppleSignIn("<your client_id>").validate("<the identity token>")

// If the identity token is expired, etc. the validate() method will return null.
// If there is a network issue or the given identity token is no valid JWT token at all, an exception will be thrown.

println(result.userIdentifier) // The unique identifier of the user.
println(result.email) // The email address of the user.
println(result.emailVerified) // If the email address is verified.
println(result.issuedAt) // The time the token was issued.
```
