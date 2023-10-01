package com.mesum.todolist.util

import android.Manifest.permission.POST_NOTIFICATIONS


import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mesum.todolist.MainActivity


val requestCode = 98765

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun requestNotificationPermission(context: Context, callback: () -> Unit) {
    val permission = POST_NOTIFICATIONS
    val granted = PackageManager.PERMISSION_GRANTED

    if (ContextCompat.checkSelfPermission(context, permission) != granted) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Notification Permission Required")
            .setMessage("We need permission to send task reminders.")
            .setPositiveButton("OK") { _, _ ->
                ActivityCompat.requestPermissions(
                    context as MainActivity,
                    arrayOf(permission),
                    requestCode
                )
            }
            .setNegativeButton("Cancel") { _, _ -> /* Handle cancellation if needed */ }
            .create()

        dialog.show()
    } else {
        callback()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.requestNotificationPermissionAndExecute(callback: () -> Unit) {
    requestNotificationPermission(this) {
        callback()
    }
}
