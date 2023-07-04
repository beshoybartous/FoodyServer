package com.foody.features.authentication.user.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    val userName: String,
    val email: String,
    val password: String,
    val imageBase64: String,
    val salt: String,
    @BsonId val id: ObjectId = ObjectId()
)