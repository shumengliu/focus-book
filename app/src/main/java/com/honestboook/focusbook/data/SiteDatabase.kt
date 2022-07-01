package com.honestboook.focusbook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.honestboook.focusbook.model.Site

@Database(entities = [Site::class], version = 2)
abstract class SiteDatabase : RoomDatabase() {

    abstract fun siteDao(): SiteDao

    companion object {
        @Volatile
        private var INSTANCE: SiteDatabase? = null

        fun getDatabase(context: Context): SiteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        SiteDatabase::class.java,
                        "focus-book.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }
    }
}