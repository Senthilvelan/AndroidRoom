package com.sen.cooey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sen.cooey.storage.db.repo.Repo
import com.sen.cooey.storage.db.table.CardDataEntity
import com.sen.cooey.storage.db.table.UserEntity

class MyViewModel(application: Application) : AndroidViewModel(application) {

    var list: LiveData<List<UserEntity>>
    var listCard: LiveData<List<CardDataEntity>>

    val repo = Repo(application)


    init {
        list = repo.list
        listCard = repo.listCard
    }

    fun insert(userEntity: UserEntity) {
        repo.insert(userEntity)
    }

    fun update(userEntity: UserEntity) {
        repo.update(userEntity)
    }

    fun delete(userEntity: UserEntity) {
        repo.delete(userEntity)
    }

    fun getUsers(): LiveData<List<UserEntity>> {
        list = repo.getUsers()
        return list
    }


    fun insert(userEntity: CardDataEntity) {
        repo.insert(userEntity)
    }

    fun update(userEntity: CardDataEntity) {
        repo.update(userEntity)
    }

    fun delete(userEntity: CardDataEntity) {
        repo.delete(userEntity)
    }

    fun getCardData(): LiveData<List<CardDataEntity>> {
        listCard = repo.getCardData()
        return listCard
    }
}