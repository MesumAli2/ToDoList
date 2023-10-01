package com.mesum.todolist.ui.tasks

import com.mesum.todolist.data.Task

data class TasksListViewState(
    val allTasks: List<Task>,
    val selectedTaskId: String? = null,
    val loadingTasks: Boolean? = false
) : com.mesum.todolist.redux.State {

    companion object {
        fun idle(): TasksListViewState {
            return TasksListViewState(
                allTasks = emptyList(),
                selectedTaskId = null,
                loadingTasks = false
            )
        }
    }
}
