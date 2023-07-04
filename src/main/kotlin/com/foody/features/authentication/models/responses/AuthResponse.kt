package com.foody.features.authentication.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token:String
)
