package com.mesum.todolist.domain.usecase

import com.mesum.todolist.data.Task
import com.mesum.todolist.domain.repository.TasksRepository
import com.mesum.todolist.redux.UseCase
import javax.inject.Inject

class LoadTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) : UseCase<Unit,MutableList<Task>>() {
    override suspend fun execute(unit: Unit): MutableList<Task> {
        // Load tasks from your repository or data source
        // Create a ViewTaskViewState based on the loaded tasks
        return mutableListOf()
    }

}

