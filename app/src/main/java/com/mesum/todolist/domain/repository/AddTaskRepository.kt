package com.mesum.todolist.domain.repository

import com.mesum.todolist.data.Tasks
import com.mesum.todolist.domain.entity.AddTask
import com.mesum.todolist.ui.addtask.AddTaskViewState

interface AddTaskRepository {

    suspend fun createTask(task : AddTaskViewState) : Boolean

}