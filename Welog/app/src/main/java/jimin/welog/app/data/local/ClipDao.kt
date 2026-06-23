package jimin.com.welog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ClipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(clip: ClipEntity)

    @Query("SELECT * FROM clips WHERE date = :date ORDER BY hour DESC")
    fun observeByDate(date: String): Flow<List<ClipEntity>>

    @Query("SELECT * FROM clips WHERE date = :date ORDER BY hour ASC, createdAt DESC")
    suspend fun getByDate(date: String): List<ClipEntity>

    @Query("SELECT * FROM clips WHERE date = :date ORDER BY hour DESC LIMIT 1")
    suspend fun latestForDate(date: String): ClipEntity?
}
