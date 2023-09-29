package com.mesum.todolist.ui.action
import com.mesum.todolist.data.Task
import com.mesum.todolist.data.Tasks
import com.mesum.todolist.redux.Action
import com.mesum.todolist.ui.tasks.ViewTaskViewState
import kotlinx.coroutines.flow.Flow

sealed class ViewTaskAction : Action {
    // Action to indicate the start of task loading
    object LoadTasksStarted : ViewTaskAction()

    // Action to signal task loading completion
    data class LoadTasksCompleted(val tasks: MutableList<Task>) : ViewTaskAction()

    // Action to filter tasks by category
    data class FilterTasksByCategory(val category: String) : ViewTaskAction()

    // Action to sort tasks
    // data class SortTasks(val sortBy: SortBy) : ViewTaskAction()

    // Action to select a task for detailed view
    data class SelectTask(val taskId: String) : ViewTaskAction()

    // Action to clear the selected task
    object ClearSelectedTask : ViewTaskAction()
}

