package com.mesum.todolist.ui.tasks

import com.mesum.todolist.data.Task

data class ViewTaskViewState(
    val allTasks: List<Task>,
    val selectedTaskId: String? = null,
    val loadingTasks: Boolean? = false
) : com.mesum.todolist.redux.State {

    companion object {
        fun idle(): ViewTaskViewState {
            return ViewTaskViewState(
                allTasks = emptyList(),
                selectedTaskId = null,
                loadingTasks = false
            )
        }
    }
}
