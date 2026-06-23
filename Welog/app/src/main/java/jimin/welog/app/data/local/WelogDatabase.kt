package jimin.com.welog.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ClipEntity::class], version = 1, exportSchema = false)
abstract class WelogDatabase : RoomDatabase() {
    abstract fun clipDao(): ClipDao

    companion object {
        @Volatile
        private var instance: WelogDatabase? = null

        fun get(context: Context): WelogDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    WelogDatabase::class.java,
                    "welog.db"
                ).build().also { instance = it }
            }
        }
    }
}
