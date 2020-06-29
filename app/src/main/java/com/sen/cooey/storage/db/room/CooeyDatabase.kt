package com.sen.cooey.storage.db.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sen.cooey.storage.db.dao.CardDataDao
import com.sen.cooey.storage.db.dao.UserDao
import com.sen.cooey.storage.db.table.CardDataEntity
import com.sen.cooey.storage.db.table.UserEntity
import com.sen.cooey.utils.AppConstants

@Database(version = 1, entities = [UserEntity::class, CardDataEntity::class])
abstract class CooeyDatabase : RoomDatabase() {

    companion object {
        fun get(application: Application): CooeyDatabase {
            return Room.databaseBuilder(
                application,
                CooeyDatabase::class.java,
                AppConstants.DB_NAME
            )
                .build()
        }
    }

    abstract fun getUserDao(): UserDao

    abstract fun getCardDataDao(): CardDataDao

}