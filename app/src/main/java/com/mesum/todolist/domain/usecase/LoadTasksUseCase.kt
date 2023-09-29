package com.mesum.todolist.domain.usecase

import com.mesum.todolist.domain.repository.TasksRepository
import com.mesum.todolist.redux.UseCase
import com.mesum.todolist.ui.tasks.ViewTaskViewState
import javax.inject.Inject

class LoadTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository // Replace with your actual repository
) : UseCase<Unit, ViewTaskViewState>() {

    override suspend fun execute(input: Unit): ViewTaskViewState {
        // Load tasks from your repository or data source
        val tasks = tasksRepository.loadTasks()

        // Create a ViewTaskViewState based on the loaded tasks
        val viewState = ViewTaskViewState(
            allTasks = tasks,
            completedTasks = tasks,
            activeTasks = tasks
        )

        return viewState
    }
}
