package com.mesum.todolist.domain.repository

import com.mesum.todolist.data.Task

interface TasksRepository {
    suspend fun loadTasks(): List<Task>
}
