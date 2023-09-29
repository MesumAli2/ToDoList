package com.mesum.todolist.ui.reducer

import com.mesum.todolist.redux.Reducer
import com.mesum.todolist.ui.action.ViewTaskAction
import com.mesum.todolist.ui.tasks.ViewTaskViewState

class ViewTaskReducer : Reducer<ViewTaskViewState, ViewTaskAction> {
    override fun reduce(currentState: ViewTaskViewState, action: ViewTaskAction): ViewTaskViewState {
        return when (action) {
            is ViewTaskAction.LoadTasksStarted -> {
                // Handle the start of task loading and update the state accordingly
                startLoadingTasks(currentState)
            }

//            is ViewTaskAction.FilterTasksByCategory -> {
//                // Handle filtering tasks by category and update the state accordingly
//                filterTasksByCategory(currentState, action)
//            }
//            is ViewTaskAction.SortTasks -> {
//                // Handle sorting tasks and update the state accordingly
//                sortTasks(currentState, action)
//            }
            is ViewTaskAction.LoadTasksCompleted -> {
                // Handle loading tasks from a data source and update the state accordingly
                loadTasks(currentState, action)
            }
            is ViewTaskAction.SelectTask -> {
                // Handle selecting a task for detailed view and update the state accordingly
                selectTask(currentState, action)
            }
            is ViewTaskAction.ClearSelectedTask -> {
                // Handle clearing the selected task and update the state accordingly
                clearSelectedTask(currentState)
            }

            // Handle other actions related to viewing tasks here
            else -> currentState
        }
    }

        private fun startLoadingTasks(currentState: ViewTaskViewState): ViewTaskViewState {
                // Update the state to indicate that task loading has started
            return currentState.copy(loadingTasks = true)
    }


    private fun loadTasks(currentState: ViewTaskViewState, action: ViewTaskAction.LoadTasksCompleted): ViewTaskViewState {
        // You receive the loaded tasks from the action
        val loadedTasks = action.tasks

        // Update the state with the loaded tasks
        return currentState.copy(
            allTasks = loadedTasks.allTasks,
            completedTasks = loadedTasks.completedTasks,
            activeTasks = loadedTasks.activeTasks
        )
    }



//    private fun filterTasksByCategory(
//        currentState: ViewTaskViewState,
//        action: ViewTaskAction.FilterTasksByCategory
//    ): ViewTaskViewState {
//        // Apply filtering logic based on the selected category and update the state
//        val filteredTasks = // Filter tasks based on the category
//            return currentState.copy(filteredTasks = filteredTasks)
//    }

//    private fun sortTasks(
//        currentState: ViewTaskViewState,
//        action: ViewTaskAction.SortTasks
//    ): ViewTaskViewState {
//        // Apply sorting logic based on the specified sorting criteria and update the state
//        val sortedTasks = // Sort tasks based on the criteria
//            return currentState.copy(sortedTasks = sortedTasks)
//    }

    private fun selectTask(
        currentState: ViewTaskViewState,
        action: ViewTaskAction.SelectTask
    ): ViewTaskViewState {
        // Update the state to indicate that a task has been selected for detailed view
        return currentState.copy(selectedTaskId = action.taskId)
    }

    private fun clearSelectedTask(currentState: ViewTaskViewState): ViewTaskViewState {
        // Clear the selected task from the state
        return currentState.copy(selectedTaskId = null)
    }
}
