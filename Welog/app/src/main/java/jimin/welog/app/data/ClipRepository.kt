package jimin.com.welog.data

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import jimin.com.welog.data.local.ClipDao
import jimin.com.welog.data.local.ClipEntity
import jimin.com.welog.data.local.WelogDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HourSlot(
    val hour: Int,
    val ljm: ClipEntity?,
    val jsh: ClipEntity?
)

class ClipRepository private constructor(
    private val context: Context,
    private val dao: ClipDao
) {
    private val firebaseRoot = FirebaseDatabase.getInstance().reference.child("clips")

    suspend fun saveClip(
        userId: String,
        caption: String,
        sourcePath: String,
        createdAt: Long = System.currentTimeMillis()
    ): ClipEntity {
        val date = dateKey(createdAt)
        val hour = hour(createdAt)
        val fileName = "${userId}_${date}_${"%02d".format(hour)}.mp4"
        val outputFile = File(context.filesDir, fileName)
        File(sourcePath).copyTo(outputFile, overwrite = true)
        val entity = ClipEntity(
            date = date,
            hour = hour,
            userID = userId,
            localPath = outputFile.absolutePath,
            caption = caption.take(20),
            isUploaded = 0,
            createdAt = createdAt
        )
        dao.upsert(entity)
        uploadToFirebase(entity)
        return entity
    }

    fun observeToday(): Flow<List<ClipEntity>> = dao.observeByDate(dateKey(System.currentTimeMillis()))

    fun observeTodaySlots(): Flow<List<HourSlot>> {
        val date = dateKey(System.currentTimeMillis())
        return dao.observeByDate(date).map { toHourSlots(it) }
    }

    suspend fun getTodaySlots(): List<HourSlot> {
        val date = dateKey(System.currentTimeMillis())
        return toHourSlots(dao.getByDate(date))
    }

    private fun uploadToFirebase(entity: ClipEntity) {
        val bytes = File(entity.localPath).readBytes()
        val base64 = android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)
        firebaseRoot.child(entity.date).child(entity.userID).child("%02d".format(entity.hour))
            .setValue(
                mapOf(
                    "videoBase64" to base64,
                    "caption" to entity.caption,
                    "uploadedAt" to System.currentTimeMillis()
                )
            )
    }

    private fun dateKey(ms: Long): String = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date(ms))
    private fun hour(ms: Long): Int = SimpleDateFormat("HH", Locale.KOREA).format(Date(ms)).toInt()
    fun todayDateLabel(): String = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(Date())

    private fun toHourSlots(clips: List<ClipEntity>): List<HourSlot> {
        if (clips.isEmpty()) return emptyList()
        return clips
            .groupBy { it.hour }
            .toSortedMap()
            .map { (hour, hourClips) ->
                HourSlot(
                    hour = hour,
                    ljm = hourClips.filter { it.userID == "LJM" }.maxByOrNull { it.createdAt },
                    jsh = hourClips.filter { it.userID == "JSH" }.maxByOrNull { it.createdAt }
                )
            }
    }

    companion object {
        @Volatile
        private var instance: ClipRepository? = null
        fun get(context: Context): ClipRepository {
            return instance ?: synchronized(this) {
                instance ?: ClipRepository(context, WelogDatabase.get(context).clipDao()).also { instance = it }
            }
        }
    }
}
