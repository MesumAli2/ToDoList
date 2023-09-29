package com.mesum.todolist.domain.usecase

import com.mesum.todolist.data.Task
import com.mesum.todolist.data.Tasks
import com.mesum.todolist.domain.repository.TasksRepository
import com.mesum.todolist.redux.UseCase
import com.mesum.todolist.redux.UseCases
import com.mesum.todolist.ui.tasks.ViewTaskViewState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) : UseCases<MutableList<Task>>() {
    override suspend fun execute(): MutableList<Task> {
        // Load tasks from your repository or data source
        val tasks = mutableListOf<Task>()

        // Create a ViewTaskViewState based on the loaded tasks
        return tasks
    }


}

