package com.aaidhah.nadeem.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aaidhah.nadeem.model.task.Task

@Database(entities = [Task::class], version = 3, exportSchema = false)
abstract class AaidhahDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}