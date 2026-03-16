package com.finalproject.dnd_track.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CharacterEntry::class,
        ResourceOption::class,
        CharacterResource::class
    ],
    version = 1
)
abstract class DndDatabase : RoomDatabase() {
    abstract fun characterDAO(): CharacterDAO
    abstract fun resourceOptionDAO(): ResourceOptionDAO
    abstract fun characterResourceDAO(): CharacterResourceDAO

    companion object {
        @Volatile
        private var INSTANCE: DndDatabase? = null

        fun getDatabase(context: Context): DndDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DndDatabase::class.java,
                    "dnd_tracker.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}