package com.mesum.todolist.ui.addtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mesum.todolist.R



class AddTaskFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_update_task, container, false)
    }

    companion object {
        const val ARGUMENT_EDIT_TASK_ID = "Update_TASK_ID"

        operator fun invoke(): AddTaskFragment = AddTaskFragment()
    }
}