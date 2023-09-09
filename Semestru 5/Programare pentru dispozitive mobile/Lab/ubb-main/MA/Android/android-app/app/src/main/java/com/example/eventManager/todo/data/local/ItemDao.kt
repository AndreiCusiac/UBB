package com.example.eventManager.todo.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eventManager.todo.data.Item


@Dao
interface ItemDao {
    @Query("SELECT * from items ORDER BY isReturned ASC, date ASC")
    fun getAll(): LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE _id=:id ")
    fun getById(id: String): LiveData<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(specialEvent: Item)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(specialEvents: Item)

    @Query("DELETE FROM items")
    suspend fun deleteAll()

}