package jimin.com.welog.core.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import jimin.com.welog.MainActivity
import jimin.com.welog.R

class WelogAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val hour = intent?.getIntExtra("hour", 9) ?: 9
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "welog_hourly"
        manager.createNotificationChannel(
            NotificationChannel(channelId, "Welog", NotificationManager.IMPORTANCE_DEFAULT)
        )
        val launchIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("open_camera_tab", true)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            hour + 100,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val text = "\uD83D\uDCF7 ${"%02d".format(hour)}:00 — 지금 뭐 해?"
        manager.notify(
            hour,
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Welog")
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        )
    }
}
