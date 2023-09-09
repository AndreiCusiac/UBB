package com.example.eventManager.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "items2")
data class Item2(
    @PrimaryKey @ColumnInfo(name = "_id") var _id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "quantity") var quantity: Array<String>,
    @ColumnInfo(name = "status") var status: String
) {
    override fun toString(): String = name
}