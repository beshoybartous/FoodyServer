package com.foody.features.authentication.security.token.repository

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.foody.features.authentication.security.token.models.TokenClaim
import com.foody.features.authentication.security.token.models.TokenConfig
import java.util.*

class JwtTokenService : TokenService {
    override fun generateToken(config: TokenConfig, vararg claim: TokenClaim): String {
        var token = JWT.create()
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))
        claim.forEach { tokenClaim ->
            token = token.withClaim(tokenClaim.name, tokenClaim.value)
        }
        return token.sign(Algorithm.HMAC256(config.secret))
    }

}