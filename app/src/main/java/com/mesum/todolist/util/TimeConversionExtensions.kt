package com.mesum.todolist.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.convertTime(inputTime: String): String {
    val inputFormat = SimpleDateFormat("hh:mm a", Locale.US)
    val outputFormat = SimpleDateFormat("h a", Locale.US)
    val time = inputFormat.parse(inputTime)
    return outputFormat.format(time)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.setReminder(taskId: String, dateString: String, timeString: String,
                        taskName: String, category: String) {

    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(this, AlarmReceiver::class.java)
    intent.putExtra("task_id", taskId)
    intent.putExtra("taskName", taskName)
    intent.putExtra("taskCategory", category)

    val dateTimestamp = convertDateToTimestamp(dateString)
    val timeTimestamp = convertTimeToTimestamp(timeString)
    val dateTimeTimestamp = dateTimestamp + timeTimestamp

    Log.d("CompleteTimeStamp", dateTimeTimestamp.toString())

    val pendingIntent = PendingIntent.getBroadcast(
        this,
        taskId.hashCode(),
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        dateTimeTimestamp,
        pendingIntent
    )
}
