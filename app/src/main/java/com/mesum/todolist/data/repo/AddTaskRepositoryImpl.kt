package com.mesum.todolist.data.repo

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.mesum.todolist.data.datastore.DataStoreManager.dataStore
import com.mesum.todolist.domain.entitymapper.AddTaskEntityMapper
import com.mesum.todolist.domain.repository.AddTaskRepository
import com.mesum.todolist.ui.createTask.AddTaskViewState
import com.mesum.todolist.util.setReminder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject


class AddTaskRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val addTaskEntityMapper: AddTaskEntityMapper
) : AddTaskRepository{

    override suspend fun createTask(task: AddTaskViewState): Boolean {
        return try {
            context.dataStore.updateData { currentData ->
                val updatedTasks = currentData.tasks.toMutableList()
                updatedTasks.add(addTaskEntityMapper.map(task))
                Log.d("TaskAdded", currentData.toString())
                currentData.copy(tasks = updatedTasks)
            }
            // Introduce a delay of 700 million milliseconds (700,000 seconds)
            delay(700)
            // If the updateData function doesn't throw an exception, it was successful
            true
        } catch (e: Exception) {
            // Handle any exceptions or errors that may occur during the update
            Log.d("TaskAdded", e.message.toString())
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun createReminder(task: AddTaskViewState): Boolean {
        task.dueDate?.let { dueDate ->
            task.time?.let { time ->
                if (dueDate.isNotBlank() && time.isNotBlank()) {
                    context.setReminder(task.id, dueDate, time, task.title.toString(), task.category.toString())
                    return true
                }
            }
        }
        return false
    }

}

