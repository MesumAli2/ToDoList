package com.mesum.todolist.redux.state

import com.mesum.todolist.data.Task

data class TaskListState(
    val tasks: List<Task>,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
