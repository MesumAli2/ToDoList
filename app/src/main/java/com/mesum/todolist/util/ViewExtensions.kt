package com.mesum.todolist.util

import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatSpinner

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

