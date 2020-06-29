package com.sen.cooey.storage.db.dao

import androidx.room.*
import com.sen.cooey.storage.db.table.CardDataEntity

@Dao
interface CardDataDao {

    @Insert
    fun insert(user: CardDataEntity)

    @Update
    fun update(user: CardDataEntity)

    @Delete
    fun delete(user: CardDataEntity)

    @Query("SELECT * FROM CardDataEntity")
    fun getAllCardData(): List<CardDataEntity>
}