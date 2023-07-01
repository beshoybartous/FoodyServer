package com.example.features.authentication.routes

import com.example.features.authentication.models.requests.AuthRequest
import com.example.features.authentication.security.hashing.repository.HashingService
import com.example.features.authentication.user.models.User
import com.example.features.authentication.user.repository.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signup") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val areFieldsBlank = request.userName.isBlank() || request.password.isBlank()
        val isPwTooShort = request.password.length <= 8
        if (areFieldsBlank || isPwTooShort) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        val saltedHash = hashingService.generateSaltHash(request.password)
        val user = User(
            userName = request.userName,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )
        val wasAcknowledged = userDataSource.insertNewUser(user)
        if (!wasAcknowledged) {
            call.respond(
                HttpStatusCode.Conflict
            )
            return@post
        } else
            call.respond(HttpStatusCode.OK)
    }
}