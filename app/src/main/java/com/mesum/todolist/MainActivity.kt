package com.mesum.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mesum.todolist.util.requestNotificationPermissionAndExecute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestNotificationPermissionAndExecute {
            // Your logic here (e.g., setting up alarms)
            Toast.makeText(this, "Thanks", Toast.LENGTH_SHORT).show()

        }
    }


}