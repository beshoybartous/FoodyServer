package com.foody.features.authentication.user.repository

import com.foody.features.authentication.user.models.User

interface UserDataSource {
    suspend fun getUserByUserName(userName: String): User?
    suspend fun insertNewUser(user: User): Boolean
}