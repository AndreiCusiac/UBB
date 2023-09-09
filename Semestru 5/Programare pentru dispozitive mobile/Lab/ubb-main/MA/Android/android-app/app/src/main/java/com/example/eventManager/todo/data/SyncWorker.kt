package com.example.eventManager.todo.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.eventManager.core.TAG
import com.example.eventManager.todo.data.remote.ItemsApi
import java.util.*
import java.util.concurrent.TimeUnit

class SyncWorker (context: Context,
                  workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        //val connected =
        val operation = inputData.getString("operation")
        val id = inputData.getString("id").orEmpty()
        val title = inputData.getString("title").orEmpty()
        val isbn = inputData.getInt("isbn", 0)
        val isReturned = inputData.getBoolean("isReturned", false)
        val dateDay = inputData.getInt("dateDay", 1)
        val dateMonth = inputData.getInt("dateMonth", 1)
        val dateYear = inputData.getInt("dateYear", 2023)
        val date = Date(dateYear, dateMonth, dateDay)

        val e = Item(id, title, isbn, date, isReturned)

        try {
            Log.v(TAG, "sync - started")
            if(operation.equals("save")){
                val createdSpecialEvent = ItemsApi.service.create(e)
            }
            else if(operation.equals("update")){
                val updatedSpecialEvent = ItemsApi.service.update(id, e)
            }

            Log.v(TAG, "sync - succeeded")
            return Result.success()
        } catch (e: Exception) {
            Log.w(TAG, "sync - failed", e)
            return Result.failure()
        }
    }

}
