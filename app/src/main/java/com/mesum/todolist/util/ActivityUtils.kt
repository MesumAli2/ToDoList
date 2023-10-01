/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mesum.todolist.util

import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.dataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.mesum.todolist.data.datastore.TaskStateSerializer
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
@SuppressLint("CommitTransaction")
fun addFragmentToActivity(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    frameId: Int) {
  fragmentManager.beginTransaction().run {
    add(frameId, fragment)
    commit()
  }
}

val Context.dataStore by dataStore("tasks.json", TaskStateSerializer)



fun Fragment.setupToolbarWithNavigation(
    toolbarId: Int,
    title: String?,
    displayHomeAsUpEnabled: Boolean = true
) {
    val toolbar = view?.findViewById<Toolbar>(toolbarId)
    val appCompatActivity = activity as? AppCompatActivity

    appCompatActivity?.apply {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
        supportActionBar?.title = title
    }

    toolbar?.setNavigationOnClickListener {
        findNavController().navigateUp()
    }
}

fun Context.showDatePickerDialog(dateSetListener: DatePickerDialog.OnDateSetListener) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        this,
        dateSetListener,
        year,
        month,
        dayOfMonth
    )
    datePickerDialog.show()
}


fun Context.showTimePickerDialog(timeSetListener: TimePickerDialog.OnTimeSetListener) {
    val calendar = Calendar.getInstance()
    val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        this,
        timeSetListener,
        hourOfDay,
        minute,
        android.text.format.DateFormat.is24HourFormat(this)
    )
    timePickerDialog.show()
}



fun View.showDatePickerDialog(activity: FragmentActivity, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val constraints = limitRange(currentYear, currentMonth, currentDayOfMonth)

    val builder = MaterialDatePicker.Builder.datePicker()
        .setCalendarConstraints(constraints)
        .setTitleText("Select a Date")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())

    val picker = builder.build()

    picker.addOnPositiveButtonClickListener { selectedDate ->
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(Date(selectedDate))
        if (this is AppCompatEditText) {
            this.setText(formattedDate)
        } else if (this is androidx.appcompat.widget.AppCompatTextView) {
            this.text = formattedDate
        }
        onDateSelected(formattedDate)
    }

    picker.show(activity.supportFragmentManager, picker.toString())
}

private fun limitRange(year: Int, month: Int, dayOfMonth: Int): CalendarConstraints {
    val constraintsBuilder = CalendarConstraints.Builder()
    val calendarStart = Calendar.getInstance()
    calendarStart.set(year, month, dayOfMonth)
    constraintsBuilder.setStart(calendarStart.timeInMillis)

    val calendarEnd = Calendar.getInstance()
    calendarEnd.set(Calendar.YEAR, year + 1)
    constraintsBuilder.setEnd(calendarEnd.timeInMillis)

    constraintsBuilder.setValidator(DateValidatorPointForward.now())

    return constraintsBuilder.build()
}


fun View.showTimePickerDialog(activity: FragmentActivity, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    if (this is AppCompatEditText) {
        val timePickerDialog = TimePickerDialog(
            activity,
            { _, hourOfDay, minute ->
                val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val selectedTime = getTimeInMillis(hourOfDay, minute)
                val formattedTime = timeFormat.format(selectedTime)
                this.setText(formattedTime)
                onTimeSelected(formattedTime)
            },
            currentHour,
            currentMinute,
            false
        )

        timePickerDialog.show()
    }
}

private fun getTimeInMillis(hourOfDay: Int, minute: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
    calendar.set(Calendar.MINUTE, minute)
    return calendar.timeInMillis
}

fun AppCompatSpinner.onItemSelected(callback: (String) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position >= 0 && position < adapter.count) {
                val selectedItem = adapter.getItem(position).toString()
                callback(selectedItem)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Handle the case where nothing is selected (if needed)
        }
    }
}
fun RadioGroup.getSelectedRadioButton(): RadioButton? {
    val checkedRadioButtonId = checkedRadioButtonId
    return if (checkedRadioButtonId != -1) {
        findViewById(checkedRadioButtonId)
    } else {
        null
    }
}

fun RadioGroup.onRadioButtonSelected(callback: (RadioButton?) -> Unit) {
    setOnCheckedChangeListener { _, checkedId ->
        val selectedRadioButton = findViewById<RadioButton>(checkedId)
        callback(selectedRadioButton)
    }
}


// Step 1: Create a function to request permissions and show the alert dialog
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun requestNotificationPermission(context: Context, callback: () -> Unit) {

    val permission = POST_NOTIFICATIONS
    val granted = PackageManager.PERMISSION_GRANTED

    if (ContextCompat.checkSelfPermission(context, permission) != granted) {
        // Explain the purpose of the permission to the user
        val dialog = AlertDialog.Builder(context)
            .setTitle("Notification Permission Required")
            .setMessage("We need permission to send task reminders.")
            .setPositiveButton("OK") { _, _ ->
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(permission),
                    requestCode
                )
            }
            .setNegativeButton("Cancel") { _, _ -> /* Handle cancellation if needed */ }
            .create()

        dialog.show()
    } else {
        // Permission already granted, continue with your logic
        callback()
    }
}

// Step 2: Create an extension function for Context
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.requestNotificationPermissionAndExecute(callback: () -> Unit) {
    requestNotificationPermission(this) {
        // Permission granted or already granted, execute your logic here
        callback()
    }
}



 val requestCode = 98765
