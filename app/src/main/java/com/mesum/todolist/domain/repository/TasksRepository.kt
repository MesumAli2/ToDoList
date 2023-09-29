package com.mesum.todolist.domain.repository

import com.mesum.todolist.data.Task
import com.mesum.todolist.data.Tasks
import java.util.concurrent.Flow

interface TasksRepository {
    suspend fun loadTasks(): MutableList<Task>
    // You can add other methods for task-related operations here
}
