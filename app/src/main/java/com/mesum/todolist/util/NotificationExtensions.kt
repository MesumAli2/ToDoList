package com.mesum.todolist.util


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.mesum.todolist.MainActivity
import com.mesum.todolist.R
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
fun createNotification(context: Context, taskName: String?, taskCategory: String?) {
    // Create a notification channel (required on Android Oreo and above)
    val channelId = "task_notification_channel"
    val channelName = "Task Notifications"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(channelId, channelName, importance)
    val notificationManager = context.getSystemService(NotificationManager::class.java)
    notificationManager.createNotificationChannel(channel)

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as MainActivity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            requestCode
        )
        return
    }

    // Create a notification
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.create_task_24)
        .setLights(0x4CAF50, 1000, 1000)
        .setContentTitle("Task Reminder")
        .setContentText("Task: $taskName\nCategory: $taskCategory")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(longArrayOf(1000, 1000, 1000)) // Vibrate pattern (in milliseconds)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

    // Show the notification
    with(NotificationManagerCompat.from(context)) {
        notify(Random.nextInt(), builder.build())
    }
}
