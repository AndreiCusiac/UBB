package com.example.eventManager.todo.specialEvent

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.example.eventManager.core.TAG
import com.example.eventManager.todo.data.Item
import com.example.eventManager.todo.data.ItemsRepository
import kotlinx.coroutines.launch
import com.example.eventManager.core.Result
import com.example.eventManager.todo.data.SyncWorker
import com.example.eventManager.todo.data.local.ItemsDatabase
import java.util.*

class SpecialEventEditViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableItem = MutableLiveData<Item>().apply { value = Item("", "", 0, Date(),false) }

    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val specialEvent: LiveData<Item> = mutableItem

    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    val itemsRepository: ItemsRepository
    init {
        val specialEventDao = ItemsDatabase.getDatabase(application, viewModelScope).itemDao()
        itemsRepository = ItemsRepository(specialEventDao)
    }

    private val TAG_OUTPUT = "OUTPUT"

    fun getItemById(itemId: String): LiveData<Item> {
        Log.v(TAG, "getItemById...")
        return itemsRepository.getById(itemId)
    }

    fun saveOrUpdateItem(item: Item) {
        viewModelScope.launch {
            viewModelScope.launch {
                Log.v(TAG, "saveOrUpdateItem...");

                mutableFetching.value = true
                mutableException.value = null

                val result: Result<Item>
                var operation = ""

                if (item._id.isNotEmpty()) {
                    operation = "update"
                    result = itemsRepository.update(item)
                } else {
                    var id = generateRandomString(10)
                    item._id = id
                    operation = "save"
                    result = itemsRepository.save(item)
                }
                when (result) {
                    is Result.Success -> {
                        Log.d(TAG, "saveOrUpdateItem succeeded");
                    }
                    is Result.Error -> {
                        Log.w(TAG, "saveOrUpdateItem failed", result.exception);
                        mutableException.value = result.exception
                    }
                }
                mutableCompleted.value = true
                mutableFetching.value = false
            }
        }
    }

    fun generateRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun createJob(operation: String, item: Item): OneTimeWorkRequest {
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
            .addTag(TAG_OUTPUT)
            .build()

        return myWork
    }
}