package com.mesum.todolist.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mesum.todolist.MainActivity
import com.mesum.todolist.R
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) {
        val taskName = intent.getStringExtra("taskName")
        val taskCategory = intent.getStringExtra("taskCategory")

        // Create and display the notification
        createNotification(context, taskName, taskCategory)
    }
}



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun createNotification(context: Context, taskName: String?, taskCategory: String?) {
    // Create a notification channel (required on Android Oreo and above)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "task_notification_channel"
        val channelName = "Task Notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    // Check if the app has the POST_NOTIFICATIONS permission
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Request the missing permission
        // You should handle the permission request result in your activity or fragment
        // This is just a placeholder to request the permission
        // Replace 'YourActivity' with the actual activity or fragment where you want to request the permission
        ActivityCompat.requestPermissions(
            MainActivity(),
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            requestCode
        )
        return
    }

    // Create a notification
    val builder = NotificationCompat.Builder(context, "task_notification_channel")
        .setSmallIcon(androidx.core.R.drawable.notification_bg_low)
        .setContentTitle("Task Reminder")
        .setContentText("Task: $taskName\nCategory: $taskCategory")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    // Show the notification
    with(NotificationManagerCompat.from(context)) {
        notify(Random.nextInt(), builder.build())
    }
}




// Function to set a reminder for a task using AlarmManager
fun Context.setReminder(taskId: String, dateString: String, timeStr: String) {
    val dateStr = dateString
    val timeStr = timeStr

    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(this, AlarmReceiver::class.java)
    intent.putExtra("task_id", taskId)

    val dateTimestamp = convertDateToTimestamp(dateStr)
    val timeTimestamp = convertTimeToTimestamp(timeStr)
    val dateTimeTimestamp = dateTimestamp + timeTimestamp

    val pendingIntent = PendingIntent.getBroadcast(
        this,
        taskId.hashCode(), // Unique ID for the pending intent
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    // Set the alarm to trigger at the specified date and time
    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        dateTimeTimestamp,
        pendingIntent,

    )
}

// Function to convert a date string in "yyyy-MM-dd" format to a timestamp
fun Context.convertDateToTimestamp(dateStr: String): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = sdf.parse(dateStr)
    return date?.time ?: 0
}

// Function to convert a time string in "hh:mm a" format to a timestamp
fun Context.convertTimeToTimestamp(timeStr: String): Long {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = sdf.parse(timeStr)
    return date?.time ?: 0
}


