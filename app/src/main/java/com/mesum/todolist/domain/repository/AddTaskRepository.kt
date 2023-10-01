package com.mesum.todolist.domain.repository

import com.mesum.todolist.ui.addtask.AddTaskViewState

interface AddTaskRepository {
    suspend fun createTask(task : AddTaskViewState) : Boolean

    suspend fun createReminder(task : AddTaskViewState) : Boolean


}