package com.example.eventManager.todo.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.eventManager.core.TAG
import com.example.eventManager.core.Result
import com.example.eventManager.todo.data.local.ItemDao
import com.example.eventManager.todo.data.remote.ItemsApi

class ItemsRepository(private val itemDao: ItemDao) {

    val specialEvents = itemDao.getAll();

    suspend fun refresh(): Result<Boolean> {
        try {
            val items = ItemsApi.service.find()
            for (item in items) {
                itemDao.insert(item)
            }
            return Result.Success(true)
        } catch (e: java.lang.Exception) {
            return Result.Error(e)
        }
    }


    fun getById(itemId: String): LiveData<Item> {
        return itemDao.getById(itemId)
    }


    suspend fun save(item: Item): Result<Item> {
        try {
            Log.v(TAG, "save - started")
            val createdItem = ItemsApi.service.create(item)
            itemDao.insert(createdItem)
            Log.v(TAG, "save - succeeded")

            //Log.v(TAG, "start worker")
            //createWorker()

            return Result.Success(createdItem)
        } catch (e: Exception) {
            Log.w(TAG, "save - failed", e)

            itemDao.insert(item)

            createWorker(item, "save")
            return Result.Error(e)
        }
    }

    suspend fun update(item: Item): Result<Item> {
        try {
            Log.v(TAG, "update - started")
            val updatedItem = ItemsApi.service.update(item._id, item)
            itemDao.update(updatedItem)
            Log.v(TAG, "update - succeeded")

            //Log.v(TAG, "start worker")
            //createWorker()

            return Result.Success(updatedItem)
        } catch (e: Exception) {
            Log.v(TAG, "update - failed")
            itemDao.update(item)

            //Log.v(TAG, "start worker")
            createWorker(item, "update")

            return Result.Error(e)
        }
    }

    fun createWorker(item: Item, operation: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val inputData = Data.Builder()
            .putString("operation", operation)
            .putString("id", item._id)
            .putString("title", item.title)
            .putInt("isbn", item.isbn)
            .putBoolean("isReturned", item.isReturned)
            .putInt("dateDay", item.date.day)
            .putInt("dateMonth", item.date.month)
            .putInt("dateYear", item.date.year)
            .build()

        val myWork = OneTimeWorkRequest.Builder(SyncWorker::class.java)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance().enqueue(myWork);
    }

}