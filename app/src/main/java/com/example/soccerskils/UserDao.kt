package com.example.soccerskils

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.List
@Dao
interface UserDao {

    @Query("SELECT * FROM mySoccers")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM mySoccers WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): User


    @Insert
    suspend fun insert(mySoccers: User)

    @Update
    suspend fun update(mySoccers: User)
}