package com.mesum.todolist.domain.repository

import com.mesum.todolist.ui.createTask.AddTaskViewState

interface AddTaskRepository {
    suspend fun createTask(task : AddTaskViewState) : Boolean

    suspend fun createReminder(task : AddTaskViewState) : Boolean


}