package com.foody.features.authentication.routes

import com.foody.features.authentication.models.requests.AuthRequest
import com.foody.features.authentication.models.responses.AuthResponse
import com.foody.features.authentication.security.hashing.models.SaltedHash
import com.foody.features.authentication.security.hashing.repository.HashingService
import com.foody.features.authentication.security.token.models.TokenClaim
import com.foody.features.authentication.security.token.models.TokenConfig
import com.foody.features.authentication.security.token.repository.TokenService
import com.foody.features.authentication.user.repository.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signIn(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig

) {
    post("signin") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val areFieldsBlank = request.userName.isNullOrBlank() || request.password.isNullOrBlank()
        if (areFieldsBlank) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        val isPwTooShort = request.password!!.length <= 8
        if (isPwTooShort) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        val user = userDataSource.getUserByUserName(request.userName!!)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        val isVerified = hashingService.verify(
            request.password!!,
            SaltedHash(
                hash = user.password,
                salt = user.salt
            )
        )
        if (!isVerified) {
            call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
            return@post
        }
        val token = tokenService.generateToken(
            tokenConfig,
            TokenClaim(name = "userID", value = user.id.toString()),
            TokenClaim(name = "userName", value = user.userName),
            TokenClaim(name = "email", value = user.email),
            TokenClaim(name = "image", value = user.imageBase64)
        )
        call.respond(status = HttpStatusCode.OK, message = AuthResponse(token = token))
    }
}