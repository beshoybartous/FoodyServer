package com.example.features.authentication.security.token.repository

import com.example.features.authentication.security.token.models.TokenClaim
import com.example.features.authentication.security.token.models.TokenConfig

interface TokenService {
    fun generateToken(
        config: TokenConfig,
        vararg claim: TokenClaim
    ): String
}