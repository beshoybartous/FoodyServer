package com.foody.plugins

import com.foody.di.authenticationKoinModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(){
    install(Koin){
        slf4jLogger()
        modules(authenticationKoinModule)
    }
}