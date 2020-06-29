package com.sen.cooey.storage.db.repo

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import com.sen.cooey.storage.db.room.CooeyDatabase.Companion.get
import com.sen.cooey.storage.db.table.CardDataEntity
import com.sen.cooey.storage.db.table.UserEntity

class Repo(val application: Application) {

    val list = MutableLiveData<List<UserEntity>>()
    val listCard = MutableLiveData<List<CardDataEntity>>()

    fun insert(userEntity: UserEntity) {
        Insert(application, userEntity).execute()
    }

    fun update(userEntity: UserEntity) {
        Update(application, userEntity).execute()
    }

    fun delete(userEntity: UserEntity) {
        Delete(application, userEntity).execute()
    }

    fun getUsers(): MutableLiveData<List<UserEntity>> {
        list.value = GetUserData(application).execute().get()
        return list
    }


    fun insert(userEntity: CardDataEntity) {
        InsertCard(application, userEntity).execute()
    }

    fun update(userEntity: CardDataEntity) {
        UpdateCard(application, userEntity).execute()
    }

    fun delete(userEntity: CardDataEntity) {
        DeleteCard(application, userEntity).execute()
    }

    fun getCardData(): MutableLiveData<List<CardDataEntity>> {
        listCard.value = GetCardData(application).execute().get()
        return listCard
    }


    private class Insert(val application: Application, val userEntity: UserEntity) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            get(application).getUserDao().insert(userEntity)
            return null
        }

    }

    private class Update(val application: Application, val userEntity: UserEntity) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            get(application).getUserDao().update(userEntity)
            return null
        }

    }

    private class Delete(val application: Application, val userEntity: UserEntity) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            get(application).getUserDao().delete(userEntity)
            return null
        }

    }

    private class GetUserData(val application: Application) :
        AsyncTask<Void, Void, List<UserEntity>>() {
        override fun doInBackground(vararg params: Void?): List<UserEntity>? {
            return get(application).getUserDao().getUsers()

        }

    }


    private class InsertCard(val application: Application, val userEntity: CardDataEntity) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            get(application).getCardDataDao().insert(userEntity)
            return null
        }

    }

    private class UpdateCard(val application: Application, val userEntity: CardDataEntity) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            get(application).getCardDataDao().update(userEntity)
            return null
        }

    }

    private class DeleteCard(val application: Application, val userEntity: CardDataEntity) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            get(application).getCardDataDao().delete(userEntity)
            return null
        }

    }

    private class GetCardData(val application: Application) :
        AsyncTask<Void, Void, List<CardDataEntity>>() {
        override fun doInBackground(vararg params: Void?): List<CardDataEntity>? {
            return get(application).getCardDataDao().getAllCardData()
        }
    }

}