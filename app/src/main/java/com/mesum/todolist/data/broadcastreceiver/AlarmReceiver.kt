package com.mesum.todolist.data.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.mesum.todolist.util.createNotification

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) {
        val taskName = intent.getStringExtra("taskName")
        val taskCategory = intent.getStringExtra("taskCategory")

        // Create and display the notification
        createNotification(context, taskName, taskCategory)
    }
}


