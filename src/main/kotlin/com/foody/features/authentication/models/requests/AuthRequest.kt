package com.example.features.authentication.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val userName:String,
    val password:String
)