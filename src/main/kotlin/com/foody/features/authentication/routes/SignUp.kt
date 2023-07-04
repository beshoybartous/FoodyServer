package com.foody.features.authentication.routes

import com.foody.features.authentication.models.requests.AuthRequest
import com.foody.features.authentication.security.hashing.repository.HashingService
import com.foody.features.authentication.user.models.User
import com.foody.features.authentication.user.repository.UserDataSource
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signup") {
        val multiPart = call.receiveMultipart()
        val authRequest = AuthRequest()

        multiPart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        "userName" -> {
                            authRequest.userName = part.value
                        }
                        "password" -> {
                            authRequest.password = part.value
                        }
                        "confirmPassword" -> {
                            authRequest.confirmPassword = part.value
                        }
                        "email" -> {
                            authRequest.email = part.value
                        }
                    }
                }
                is PartData.FileItem -> {
                    if (part.name == "image") {
                        authRequest.imageBase64 = Base64.getEncoder().encodeToString(part.streamProvider().readBytes())
                    }
                }
                else -> Unit
            }
            part.dispose()
        }

        val areFieldsBlank =
            authRequest.userName.isNullOrBlank() ||
                    authRequest.password.isNullOrBlank() ||
                    authRequest.confirmPassword.isNullOrBlank() ||
                    authRequest.email.isNullOrBlank() || authRequest.imageBase64.isNullOrBlank()

        if (areFieldsBlank) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        val isPasswordNotEqual = authRequest.password != authRequest.confirmPassword
        if (isPasswordNotEqual) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val isPwTooShort = authRequest.password!!.length <= 8

        if (isPwTooShort) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val saltedHash = hashingService.generateSaltHash(authRequest.password!!)
        val user = User(
            userName = authRequest.userName!!,
            email = authRequest.email!!,
            password = saltedHash.hash,
            imageBase64 = authRequest.imageBase64!!,
            salt = saltedHash.salt,
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