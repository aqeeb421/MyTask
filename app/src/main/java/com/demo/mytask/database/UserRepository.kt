package com.demo.mytask.database

import android.util.Log
import com.demo.mytask.database.UserDao
import com.demo.mytask.database.UserEntity

class UserRepository(private val dao: UserDao) {

    private val TAG = "UserRepository"

    val users = dao.getAllUsers()
    suspend fun insert(user: UserEntity) {
        return dao.insert(user)
    }

    suspend fun deleteUser(user: UserEntity) {
         dao.deleteSubscriber(user)
    }

    suspend fun getUserName(userName: String): UserEntity? {
        Log.i(TAG, "inside Repository Getusers fun ")
        return dao.getUsername(userName)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }

}