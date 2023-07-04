package com.foody.features.authentication.user.repository

import com.foody.features.authentication.user.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MongoUserDataSource(db: CoroutineDatabase) : UserDataSource {
    private val users = db.getCollection<User>()
    override suspend fun getUserByUserName(userName: String): User? =
        users.findOne(User::userName eq userName)


    override suspend fun insertNewUser(user: User): Boolean =
        users.insertOne(user).wasAcknowledged()
}