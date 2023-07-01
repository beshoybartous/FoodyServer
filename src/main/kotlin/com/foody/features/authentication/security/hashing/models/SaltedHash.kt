package com.example.features.authentication.security.hashing.models

data class SaltedHash(
    val hash:String,
    val salt:String
)
