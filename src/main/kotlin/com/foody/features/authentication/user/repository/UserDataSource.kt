package com.example.features.authentication.user.repository

import com.example.features.authentication.user.models.User

interface UserDataSource {
    suspend fun getUserByUserName(userName: String): User?
    suspend fun insertNewUser(user: User): Boolean
}