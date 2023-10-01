package com.mesum.todolist.ui.reducers

import com.mesum.todolist.redux.Reducer
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.createTask.AddTaskViewState

/**
 * This reducer handles all [AddTaskReducer] actions and uses them to create a new [AddTaskViewState]
 */
class AddTaskReducer : Reducer<AddTaskViewState, TaskAction> {
    override fun reduce(currentState: AddTaskViewState, action: TaskAction): AddTaskViewState {
        return when (action) {
            is TaskAction.TaskTitleChanged -> newStateWithTitle(currentState, action.newTitle)
            is TaskAction.TaskDescriptionChanged -> newStateWithDescription(currentState, action.newDescription)
            is TaskAction.TaskDueDateChanged -> newStateWithDueDate(currentState, action.newDueDate)
            is TaskAction.TaskTimeChanged -> newStateWithTime(currentState, action.newTime)
            is TaskAction.TaskCategoryChanged -> newStateWithCategory(currentState, action.newCategory)
            is TaskAction.TaskPriorityChanged -> newStateWithPriority(currentState, action.newPriority)
            is TaskAction.CreateTaskButtonClicked -> startTaskCreation(currentState)
            is TaskAction.TaskCreationStarted -> createTask(currentState)
            is TaskAction.TaskCreationCompleted -> completeTaskCreation(currentState)
            is TaskAction.TaskCreationFailed -> taskCreationFailed(currentState)
            is TaskAction.InvalidTaskTitle -> invalidTitle(currentState)
            is TaskAction.InvalidTaskDescription -> invalidDescription(currentState)
            is TaskAction.InvalidTaskCategory -> invalidCategory(currentState)
            is TaskAction.InvalidTaskPriority -> invalidPriority(currentState)
            else -> currentState
        }
    }

    private fun newStateWithTime(currentState: AddTaskViewState, newTime: String) =
        currentState.copy(time = newTime)

    private fun invalidTitle(currentState: AddTaskViewState) =
        currentState.copy(errorTitle = "Please Enter Title")

    private fun invalidDescription(currentState: AddTaskViewState) =
        currentState.copy(errorDescription = "Please Enter Description")

    private fun invalidCategory(currentState: AddTaskViewState) =
        currentState.copy(errorCategory = "Please select category")

    private fun invalidPriority(currentState: AddTaskViewState) =
        currentState.copy(errorPriority = "Please pick your priority")

    private fun taskCreationFailed(currentState: AddTaskViewState) =
        currentState.copy(
            creatingTask = false,
            errorTitle = "DataStore Entry Error"
        )

    private fun completeTaskCreation(currentState: AddTaskViewState) =
        currentState.copy(
            creatingTask = false,
            errorTitle = null,
            title = "",
            description = "",
            showProgressBar = false,
            dueDate = null,
            category = "",
            priority = "",
            taskAdded = true
        )

    private fun createTask(currentState: AddTaskViewState) =
        currentState.copy(creatingTask = true, showProgressBar = true)

    private fun startTaskCreation(currentState: AddTaskViewState) =
        currentState.copy(creatingTask = true)

    private fun newStateWithPriority(currentState: AddTaskViewState, newPriority: String) =
        currentState.copy(priority = newPriority)

    private fun newStateWithCategory(currentState: AddTaskViewState, newCategory: String) =
        currentState.copy(category = newCategory)

    private fun newStateWithDueDate(currentState: AddTaskViewState, newDueDate: String) =
        currentState.copy(dueDate = newDueDate)

    private fun newStateWithDescription(currentState: AddTaskViewState, newDescription: String) =
        currentState.copy(description = newDescription)

    private fun newStateWithTitle(currentState: AddTaskViewState, newTitle: String) =
        currentState.copy(title = newTitle, errorTitle = null)
}
