package com.mesum.todolist.domain.usecase

import com.mesum.todolist.domain.repository.AddTaskRepository
import com.mesum.todolist.redux.UseCase
import com.mesum.todolist.ui.addtask.AddTaskViewState
import javax.inject.Inject

class CreateTaskReminderUseCase  @Inject constructor(
    private val addTaskRepo: AddTaskRepository
) : UseCase<AddTaskViewState, Boolean>() {

    override suspend fun execute(task: AddTaskViewState): Boolean {
        return addTaskRepo.createReminder(task)
    }
}
