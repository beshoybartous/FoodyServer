package com.foody.di

import com.example.features.authentication.security.hashing.repository.HashingService
import com.example.features.authentication.security.hashing.repository.SHA256HashingService
import com.example.features.authentication.security.token.models.TokenConfig
import com.example.features.authentication.security.token.repository.JwtTokenService
import com.example.features.authentication.security.token.repository.TokenService
import com.example.features.authentication.user.repository.MongoUserDataSource
import com.example.features.authentication.user.repository.UserDataSource
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val authenticationKoinModule = module {
    single<UserDataSource> {
        MongoUserDataSource(get())
    }
    single<TokenService> {
        JwtTokenService()
    }
    single<HashingService> {
        SHA256HashingService()
    }

    single {
        val mongoPw = System.getenv("MONGO_PW")
        val dataBaseName = System.getenv("DATA_BASE_NAME")
        KMongo.createClient(
            connectionString = "mongodb+srv://jwtbeshoy:$mongoPw@cluster0.hhkom4e.mongodb.net/?retryWrites=true&w=majority"
        ).coroutine.getDatabase(dataBaseName)
    }

    single {
        TokenConfig(
            issuer = System.getenv("JWT_ISSUER"),
            audience = System.getenv("JWT_AUDIENCE"),
            expiresIn = 365L * 1000L * 60L * 60L * 24L,
            secret = System.getenv("JWT_SECRET")
        )
    }

}