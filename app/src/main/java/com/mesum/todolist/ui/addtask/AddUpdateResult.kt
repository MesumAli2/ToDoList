package com.mesum.todolist.ui.addtask

import com.mesum.todolist.data.Task
import com.mesum.todolist.redux.Result

sealed class AddUpdateResult : Result {
    sealed class PopulateTaskResult : AddUpdateResult() {
        data class Success(val task: Task) : PopulateTaskResult()
        data class Failure(val error: Throwable) : PopulateTaskResult()
        object InFlight : PopulateTaskResult()
    }

    sealed class CreateTaskResult : AddUpdateResult() {
        object Success : CreateTaskResult()
        object Empty : CreateTaskResult()
    }

    object UpdateTaskResult : AddUpdateResult()
}
