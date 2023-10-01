package com.mesum.todolist.util

import androidx.fragment.app.FragmentActivity
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("CommitTransaction")
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

@SuppressLint("CommitTransaction")
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

@SuppressLint("CommitTransaction")
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

@SuppressLint("CommitTransaction")
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

@SuppressLint("CommitTransaction")
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

@SuppressLint("CommitTransaction")
private fun getTimeInMillis(hourOfDay: Int, minute: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
    calendar.set(Calendar.MINUTE, minute)
    return calendar.timeInMillis
}
