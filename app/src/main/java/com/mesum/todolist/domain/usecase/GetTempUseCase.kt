package com.mesum.todolist.domain.usecase


import com.mesum.todolist.Task
import com.mesum.todolist.domain.entity.AddTask
import com.mesum.todolist.domain.repository.AddTaskRepository
import com.mesum.todolist.redux.UseCase
import com.mesum.todolist.ui.addtask.AddTaskViewState
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val addTaskRepo: AddTaskRepository
) : UseCase<AddTaskViewState>() {

    override suspend fun execute(task: AddTaskViewState) : Boolean {
        return addTaskRepo.createTask(task =  task)
    }


}