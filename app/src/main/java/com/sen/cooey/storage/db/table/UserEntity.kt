package com.sen.cooey.storage.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class UserEntity(
    val name: String,
    val email: String,
    val password: String,
    val dob: String,
    val gender: Int,
    val image: String

) {
    @PrimaryKey(autoGenerate = true)
    var key: Int = 0
}