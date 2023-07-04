package com.foody.plugins

import com.foody.features.authentication.routes.authenticate
import com.foody.features.authentication.routes.signIn
import com.foody.features.authentication.routes.signUp
import com.foody.features.authentication.security.hashing.repository.HashingService
import com.foody.features.authentication.security.token.models.TokenConfig
import com.foody.features.authentication.security.token.repository.TokenService
import com.foody.features.authentication.user.repository.UserDataSource
import com.foody.routes.rootRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val hashingService: HashingService by inject()
    val userDataSource: UserDataSource by inject()
    val tokenService: TokenService by inject()
    val tokenConfig: TokenConfig by inject()
    routing {
        rootRoute()
        signIn(hashingService, userDataSource, tokenService, tokenConfig)
        signUp(hashingService, userDataSource)
        authenticate()
    }
}