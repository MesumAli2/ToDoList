package com.mesum.todolist.ui.reducer

import android.util.Log
import com.mesum.todolist.redux.reducer.Reducer
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.addtask.AddTaskViewState

/**
 * This reducer handles all [TaskAction] actions and uses them to create a new [AddTaskViewState]
 */
class TaskReducer : Reducer<AddTaskViewState, TaskAction> {
    override fun reduce(currentState: AddTaskViewState, action: TaskAction): AddTaskViewState {
        Log.v("LogReducer","Processing action $action")
        return when (action) {
            is TaskAction.TaskTitleChanged -> {
                newStateWithTitle(currentState, action)
            }
            is TaskAction.TaskDescriptionChanged -> {
                newStateWithDescription(currentState, action)
            }
            is TaskAction.TaskDueDateChanged -> {
                newStateWithDueDate(currentState, action)
            }
            is TaskAction.TaskCategoryChanged -> {
                newStateWithCategory(currentState, action)
            }
            is TaskAction.TaskPriorityChanged -> {
                newStateWithPriority(currentState, action)
            }
            is TaskAction.CreateTaskButtonClicked -> {
                // Handles the start of task creation
                startTaskCreation(currentState)
            }
            is TaskAction.TaskCreationStarted -> {
                // Handles the  creation of the task
                createTask(currentState)
            }
            is TaskAction.TaskCreationCompleted -> {
                // Handle task creation completed (e.g., hide progress, clear inputs)
                completeTaskCreation(currentState)
            }
            is TaskAction.TaskCreationFailed -> {
                // Handle task creation failure (e.g., show error message)
                taskCreationFailed(currentState, action)
            }
            // Handle other actions for updating and managing tasks here
            else -> currentState
        }
    }

    private fun taskCreationFailed(
        currentState: AddTaskViewState,
        action: TaskAction.TaskCreationFailed
    ) = currentState.copy(creatingTask = false, error = action.error)

    private fun completeTaskCreation(currentState: AddTaskViewState) =
        currentState.copy(
            creatingTask = false,
            title = "",
            description = "",
            showProgressBar = false,
            dueDate = null,
            category = "",
            priority = 1
        )

    private fun createTask(currentState: AddTaskViewState) =
        currentState.copy(creatingTask = true, error = null, showProgressBar = true)

    private fun startTaskCreation(currentState: AddTaskViewState) =
        currentState.copy(creatingTask = true, error = null)

    private fun newStateWithPriority(
        currentState: AddTaskViewState,
        action: TaskAction.TaskPriorityChanged
    ) = currentState.copy(priority = action.newPriority)

    private fun newStateWithCategory(
        currentState: AddTaskViewState,
        action: TaskAction.TaskCategoryChanged
    ) = currentState.copy(category = action.newCategory)

    private fun newStateWithDueDate(
        currentState: AddTaskViewState,
        action: TaskAction.TaskDueDateChanged
    ) = currentState.copy(dueDate = action.newDueDate)

    private fun newStateWithDescription(
        currentState: AddTaskViewState,
        action: TaskAction.TaskDescriptionChanged
    ) = currentState.copy(description = action.newDescription)

    private fun newStateWithTitle(
        currentState: AddTaskViewState,
        action: TaskAction.TaskTitleChanged
    ) = currentState.copy(title = action.newTitle)
}

