package com.aaidhah.nadeem.di

import android.content.Context
import androidx.room.Room
import com.aaidhah.nadeem.data.database.AaidhahDatabase
import com.aaidhah.nadeem.data.database.TaskDao
import com.aaidhah.nadeem.data.datastore.ThemeManager
import com.aaidhah.nadeem.data.datastore.ThemeManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): ThemeManager {
        return ThemeManagerImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: AaidhahDatabase): TaskDao = database.getTaskDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AaidhahDatabase =
        Room.databaseBuilder(
            context,
            AaidhahDatabase::class.java,
            "task-db"
        ).fallbackToDestructiveMigration().build()
}