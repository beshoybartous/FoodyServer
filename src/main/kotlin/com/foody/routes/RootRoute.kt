package com.foody.routes

import com.foody.domain.model.EndPoints
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.rootRoute(){
    get(EndPoints.Root.path) {
        call.respondText("Welcome to Foody server")
    }
}