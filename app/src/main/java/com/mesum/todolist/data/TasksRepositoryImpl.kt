package com.mesum.todolist.data

import android.content.Context
import android.util.Log
import com.mesum.todolist.domain.repository.TasksRepository
import com.mesum.todolist.util.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TasksRepository {


    override suspend fun loadTasks(): MutableList<Task> {
      return context.dataStore.data.firstOrNull()?.tasks?.toMutableList() ?: mutableListOf()
    }

    override suspend fun markCompleted(taskID: String): Boolean {
        return try {
            context.dataStore.updateData { currentData ->
                val updatedTasks = currentData.tasks.toMutableList()
                for (index in updatedTasks.indices) {
                    if (updatedTasks[index].id == taskID) {
                        val updatedTask = updatedTasks[index].copy(isCompleted = !updatedTasks[index].isCompleted)
                        updatedTasks[index] = updatedTask
                        break
                    }
                }
                currentData.copy(tasks = updatedTasks)
            }

            true
        } catch (e: Exception) {
            // Handle any exceptions or errors that may occur during the update
            Log.d("TaskMarkedError", e.message.toString())
            false
        }
    }
}
