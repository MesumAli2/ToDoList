package com.mesum.todolist.ui.reducer

import com.mesum.todolist.redux.reducer.Reducer
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.addtask.AddUpdateViewState

class TaskReducer : Reducer<AddUpdateViewState, TaskAction> {
    override fun reduce(currentState: AddUpdateViewState, action: TaskAction): AddUpdateViewState {
        return when (action) {
            is TaskAction.TaskTitleChanged -> {
                currentState.copy(title = action.newTitle)
            }
            is TaskAction.TaskDescriptionChanged -> {
                currentState.copy(description = action.newDescription)
            }
            is TaskAction.TaskDueDateChanged -> {
                currentState.copy(dueDate = action.newDueDate)
            }
            is TaskAction.TaskCategoryChanged -> {
                currentState.copy(category = action.newCategory)
            }
            is TaskAction.TaskPriorityChanged -> {
                currentState.copy(priority = action.newPriority)
            }
            is TaskAction.CreateTaskButtonClicked -> {
                // Handles the start of task creation
                currentState.copy(creatingTask = true, error = null)
            }
            is TaskAction.TaskCreationStarted -> {
                // Handles the  creation of the task
                currentState.copy(creatingTask = true, error = null, showProgressBar = true)
            }
            is TaskAction.TaskCreationCompleted -> {
                // Handle task creation completed (e.g., hide progress, clear inputs)
                currentState.copy(
                    creatingTask = false,
                    title = "",
                    description = "",
                    showProgressBar = false,
                    dueDate = null,
                    category = "",
                    priority = 1
                )
            }
            is TaskAction.TaskCreationFailed -> {
                // Handle task creation failure (e.g., show error message)
                currentState.copy(creatingTask = false, error = action.error)
            }
            // Handle other actions for updating and managing tasks here
            else -> currentState
        }
    }
}

