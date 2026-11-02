package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        MissionProgressEntity::class,
        UnlockedHeroEntity::class,
        UserSettingsEntity::class,
        BattleAutosaveEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CenturiesDatabase : RoomDatabase() {
    abstract fun centuriesDao(): CenturiesDao

    companion object {
        @Volatile
        private var INSTANCE: CenturiesDatabase? = null

        fun getInstance(context: Context): CenturiesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CenturiesDatabase::class.java,
                    "centuries_offline_tactics.db"
                ).fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun createInMemory(context: Context): CenturiesDatabase {
            return Room.inMemoryDatabaseBuilder(
                context,
                CenturiesDatabase::class.java
            ).allowMainThreadQueries().build()
        }
    }
}
