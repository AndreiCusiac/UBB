package com.example.eventManager.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(
    @PrimaryKey @ColumnInfo(name = "_id") var _id: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "isbn") var isbn: Int,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "isReturned") var isReturned: Boolean
) {
    override fun toString(): String = title
}