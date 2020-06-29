package com.sen.cooey.storage.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class CardDataEntity(

    val picture: String,
    val name: String,
    val gender: String,
    val age: String,
    val favoriteColor: String,
    val phone: String,
    val lastSeen: String,
    val _id: String,
    val email: String,
    val liked: Int,
    var visited: Int


) {
    @PrimaryKey(autoGenerate = true)
    var key: Int = 0
}