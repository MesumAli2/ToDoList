package com.mesum.todolist.domain.usecase

import com.mesum.todolist.domain.repository.TasksRepository
import com.mesum.todolist.redux.UseCase
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) : UseCase<String, Boolean>() {
    override suspend fun execute(taskID: String): Boolean{
        return tasksRepository.deleteTask(taskID = taskID)
    }

}