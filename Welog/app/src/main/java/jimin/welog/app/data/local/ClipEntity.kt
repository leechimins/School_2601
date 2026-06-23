package jimin.com.welog.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "clips",
    indices = [Index(value = ["date", "hour", "userID"], unique = true)]
)
data class ClipEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val hour: Int,
    val userID: String,
    val localPath: String,
    val caption: String,
    val isUploaded: Int,
    val createdAt: Long
)
