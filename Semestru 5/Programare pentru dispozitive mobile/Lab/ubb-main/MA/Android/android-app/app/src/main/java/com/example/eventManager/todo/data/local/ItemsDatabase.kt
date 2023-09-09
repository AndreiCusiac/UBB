package com.example.eventManager.todo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eventManager.todo.data.Item
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Item::class], version = 1)
@TypeConverters(Converters::class)
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ItemsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ItemsDatabase {
            val inst = INSTANCE
            if (inst != null) {
                return inst
            }
            val instance =
                Room.databaseBuilder(
                    context.applicationContext,
                    ItemsDatabase::class.java,
                    "items_db"
                )
                    //.addCallback(WordDatabaseCallback(scope))
                    .build()
            INSTANCE = instance
            return instance
        }

//        private class WordDatabaseCallback(private val scope: CoroutineScope) :
//            RoomDatabase.Callback() {
//
//            override fun onOpen(db: SupportSQLiteDatabase) {
//                super.onOpen(db)
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.expenseDao())
//                    }
//                }
//            }
//        }
//
//        suspend fun populateDatabase(expenseDao: ExpenseDao) {
////            expenseDao.deleteAll()
////            val expense = Expense("1", "Hello", 0, false)
////            expenseDao.insert(expense)
//        }
    }

}