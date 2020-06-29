package com.sen.cooey.storage.db.dao

import androidx.room.*
import com.sen.cooey.storage.db.table.UserEntity

@Dao
interface UserDao {

    @Insert
    fun insert(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    //To make sure always get the last inserted data with the limit one
    @Query("SELECT * FROM UserEntity ORDER BY `key` DESC LIMIT 1 ")
    fun getUsers(): List<UserEntity>
}