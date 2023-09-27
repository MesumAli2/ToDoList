package com.mesum.todolist.ui.addtask

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

import com.mesum.todolist.R
import com.mesum.todolist.databinding.ActivityAddUpdateTaskBinding
import com.mesum.todolist.util.addFragmentToActivity

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateTaskBinding
    private lateinit var actionBar: ActionBar


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityAddUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            actionBar = this
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val taskId = intent.getStringExtra(AddTaskFragment.ARGUMENT_EDIT_TASK_ID)
        setToolbarTitle(taskId)

        if (supportFragmentManager.findFragmentById(R.id.contentFrame) == null) {
            val addEditTaskFragment = AddTaskFragment.invoke()

            if (taskId != null) {
                val args = Bundle()
                args.putString(AddTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId)
                addEditTaskFragment.arguments = args
            }

            addFragmentToActivity(supportFragmentManager, addEditTaskFragment, R.id.contentFrame)
        }


    }

    private fun setToolbarTitle(taskId: String?) {
        actionBar.setTitle(if (taskId == null) R.string.add_task else R.string.edit_task)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val REQUEST_ADD_TASK = 1
    }

}