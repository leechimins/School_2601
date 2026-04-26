package jimin.com.welog.core.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CleanupWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }.time
        val key = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(yesterday)
        FirebaseDatabase.getInstance().reference.child("clips").child(key).removeValue()
        return Result.success()
    }
}
