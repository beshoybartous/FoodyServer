package com.example.features.authentication.security.hashing.repository

import com.example.features.authentication.security.hashing.models.SaltedHash

interface HashingService {
    fun generateSaltHash(value: String, saltLength: Int = 32): SaltedHash
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}