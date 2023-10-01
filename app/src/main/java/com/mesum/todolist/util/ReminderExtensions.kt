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

// Function to convert a date string in "yyyy-MM-dd" format to a timestamp
fun Context.convertDateToTimestamp(dateStr: String): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = sdf.parse(dateStr)
    Log.d("DataTimeStamp", date.time.toString())

    return date?.time ?: 0
}

// Function to convert a time string in "hh:mm a" format to a timestamp
fun Context.convertTimeToTimestamp(timeStr: String): Long {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = sdf.parse(timeStr)
    Log.d("TimeStamp", date.time.toString())
    return date?.time ?: 0
}
