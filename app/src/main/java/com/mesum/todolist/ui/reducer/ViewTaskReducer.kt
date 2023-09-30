package com.mesum.todolist.ui.reducer

import com.mesum.todolist.data.Task
import com.mesum.todolist.redux.Reducer
import com.mesum.todolist.ui.action.ViewTaskAction
import com.mesum.todolist.ui.tasks.ViewTaskViewState

class ViewTaskReducer : Reducer<ViewTaskViewState, ViewTaskAction> {
    override fun reduce(currentState: ViewTaskViewState, action: ViewTaskAction): ViewTaskViewState {
        return when (action) {
            is ViewTaskAction.LoadTasksStarted -> {
                startLoadingTasks(currentState)
            }
            is ViewTaskAction.TaskMarkAsCompleted -> {
                markTaskAsCompleted(action.taskId, currentState)
            }
            is ViewTaskAction.DeleteTaskButtonClicked -> {
                // Handle task deletion action here
                taskDeletion(currentState)
            }
            is ViewTaskAction.TaskDeletionStarted -> {
                // Handle task deletion started action here
                taskDeletionStarted(currentState)
            }
            is ViewTaskAction.TaskDeletionCompleted -> {
                // Handle task deletion completed action here
                removeDeletedTask(action.taskId,currentState)
            }
            is ViewTaskAction.TaskDeletionFailed -> {
                // Handle task deletion failed action here
                currentState
            }
            is ViewTaskAction.LoadTasksCompleted -> {
                loadTasks(action.tasks, currentState)
            }
            is ViewTaskAction.SelectTask -> {
                selectTask(action.taskId, currentState)
            }
            is ViewTaskAction.ClearSelectedTask -> {
                clearSelectedTask(currentState)
            }
            // Handle other actions related to viewing tasks here
            else -> currentState
        }
    }

    private fun markTaskAsCompleted(
        taskId: String,
        currentState: ViewTaskViewState
    ): ViewTaskViewState {
        // Handle marking a task as completed here
        // You can update the state accordingly
        return currentState
    }

    private fun startLoadingTasks(currentState: ViewTaskViewState): ViewTaskViewState {
        // Update the state to indicate that task loading has started
        return currentState.copy(loadingTasks = true)
    }

    private fun loadTasks(
        tasks: List<Task>,
        currentState: ViewTaskViewState
    ): ViewTaskViewState {
        // Update the state with the loaded tasks
        return currentState.copy(
            allTasks = tasks,
            completedTasks = listOf(),
            activeTasks = listOf()
        )
    }

    private fun selectTask(
        taskId: String,
        currentState: ViewTaskViewState
    ): ViewTaskViewState {
        // Update the state to indicate that a task has been selected for detailed view
        return currentState.copy(selectedTaskId = taskId)
    }

    private fun clearSelectedTask(currentState: ViewTaskViewState): ViewTaskViewState {
        // Clear the selected task from the state
        return currentState.copy(selectedTaskId = null)
    }

    private fun taskDeletion(currentState: ViewTaskViewState) =
        currentState

    private fun taskDeletionStarted(currentState: ViewTaskViewState) =
        currentState

    private fun removeDeletedTask(taskId: String, currentState: ViewTaskViewState): ViewTaskViewState {
        val updatedTasks = currentState.allTasks.filter { task ->
            task.id != taskId
        }

        return currentState.copy(allTasks = updatedTasks)
    }


}
