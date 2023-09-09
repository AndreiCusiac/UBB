package com.example.eventManager.todo.specialEvents

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.eventManager.core.TAG
import com.example.eventManager.todo.data.Item
import com.example.eventManager.todo.data.ItemsRepository
import kotlinx.coroutines.launch
import com.example.eventManager.core.Result
import com.example.eventManager.todo.data.local.ItemsDatabase

class SpecialEventListViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val items: LiveData<List<Item>>
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    val itemsRepository: ItemsRepository

    init {
        val itemDao = ItemsDatabase.getDatabase(application, viewModelScope).itemDao()
        itemsRepository = ItemsRepository(itemDao)
        items = itemsRepository.specialEvents
    }

    fun refresh() {
        viewModelScope.launch {
            Log.v(TAG, "loadSpecialEvents...");
            mutableLoading.value = true
            mutableException.value = null
            when (val result = itemsRepository.refresh()) {
                is Result.Success -> {
                    Log.d(TAG, "refresh succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "refresh failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableLoading.value = false
        }
    }
}