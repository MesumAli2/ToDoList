package com.mesum.todolist.data

import android.content.Context
import com.mesum.todolist.domain.repository.TasksRepository
import com.mesum.todolist.util.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TasksRepository {


    override suspend fun loadTasks(): List<Task> {
        return try {
            val tasksData = context.dataStore.data.firstOrNull() ?: Tasks()
            tasksData.tasks // Return the list of tasks from the DataStore
        } catch (e: Exception) {
            // Handle any exceptions that may occur during the data retrieval
            emptyList()
        }
    }

}
