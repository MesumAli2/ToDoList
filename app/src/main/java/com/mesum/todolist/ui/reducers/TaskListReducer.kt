package com.mesum.todolist.ui.reducers

import com.mesum.todolist.data.Task
import com.mesum.todolist.redux.Reducer
import com.mesum.todolist.ui.action.ViewTaskAction
import com.mesum.todolist.ui.tasks.TasksListViewState

class TaskListReducer : Reducer<TasksListViewState, ViewTaskAction> {
    override fun reduce(currentState: TasksListViewState, action: ViewTaskAction): TasksListViewState {
        return when (action) {
            is ViewTaskAction.LoadTasksStarted -> startLoadingTasks(currentState)
            is ViewTaskAction.TaskMarkAsCompleted -> markTaskAsCompleted(action.taskId, currentState)
            is ViewTaskAction.DeleteTaskButtonClicked -> handleTaskDeletion(currentState)
            is ViewTaskAction.TaskDeletionStarted -> handleTaskDeletionStarted(currentState)
            is ViewTaskAction.TaskDeletionCompleted -> handleTaskDeletionCompleted(action.taskId, currentState)
            is ViewTaskAction.TaskDeletionFailed -> handleTaskDeletionFailed(currentState)
            is ViewTaskAction.TaskMarkedCompleted -> handleTaskMarkedCompleted(action.task, currentState)
            is ViewTaskAction.TasksSorted -> sortTasks(action.task, currentState)
            is ViewTaskAction.SearchQueryCompleted -> handleSearchQueryCompleted(action.searchedTasks, currentState)
            is ViewTaskAction.TasksFiltered -> handleTasksFiltered(action.task, currentState)
            is ViewTaskAction.LoadTasksCompleted -> handleLoadTasksCompleted(action.tasks, currentState)
            is ViewTaskAction.SelectTask -> selectTask(action.taskId, currentState)
            is ViewTaskAction.ClearSelectedTask -> clearSelectedTask(currentState)
            else -> currentState
        }
    }

    private fun markTaskAsCompleted(taskId: String, currentState: TasksListViewState): TasksListViewState {
        // Handle marking a task as completed here
        // You can update the state accordingly
        return currentState
    }

    private fun startLoadingTasks(currentState: TasksListViewState): TasksListViewState {
        // Update the state to indicate that task loading has started
        return currentState.copy(loadingTasks = true)
    }

    private fun handleLoadTasksCompleted(tasks: List<Task>, currentState: TasksListViewState): TasksListViewState {
        return currentState.copy(allTasks = tasks)
    }

    private fun handleTaskMarkedCompleted(tasks: List<Task>, currentState: TasksListViewState): TasksListViewState {
        return currentState.copy(allTasks = tasks)
    }

    private fun selectTask(taskId: String, currentState: TasksListViewState): TasksListViewState {
        return currentState.copy(selectedTaskId = taskId)
    }

    private fun handleSearchQueryCompleted(searchedTasks: List<Task>, currentState: TasksListViewState): TasksListViewState {
        return currentState.copy(allTasks = searchedTasks)
    }

    private fun handleTasksFiltered(filteredTasks: List<Task>, currentState: TasksListViewState): TasksListViewState {
        return currentState.copy(allTasks = filteredTasks)
    }

    private fun sortTasks(sortedTasks: List<Task>, currentState: TasksListViewState): TasksListViewState {
        return currentState.copy(allTasks = sortedTasks)
    }

    private fun clearSelectedTask(currentState: TasksListViewState): TasksListViewState {
        // Clear the selected task from the state
        return currentState.copy(selectedTaskId = null)
    }

    private fun handleTaskDeletion(currentState: TasksListViewState) = currentState

    private fun handleTaskDeletionStarted(currentState: TasksListViewState) = currentState

    private fun handleTaskDeletionCompleted(taskId: String, currentState: TasksListViewState): TasksListViewState {
        val updatedTasks = currentState.allTasks.filter { task ->
            task.id != taskId
        }

        return currentState.copy(allTasks = updatedTasks)
    }

    private fun handleTaskDeletionFailed(currentState: TasksListViewState) = currentState
}
