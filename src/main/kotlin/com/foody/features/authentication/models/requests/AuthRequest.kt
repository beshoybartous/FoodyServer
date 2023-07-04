package com.foody.features.authentication.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    var userName: String? = null,
    var password: String? = null,
    var confirmPassword: String? = null,
    var email: String? = null,
    var imageBase64: String? = null
)