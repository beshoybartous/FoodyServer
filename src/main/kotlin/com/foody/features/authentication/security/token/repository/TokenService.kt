package com.foody.features.authentication.security.token.repository

import com.foody.features.authentication.security.token.models.TokenClaim
import com.foody.features.authentication.security.token.models.TokenConfig

interface TokenService {
    fun generateToken(
        config: TokenConfig,
        vararg claim: TokenClaim
    ): String
}