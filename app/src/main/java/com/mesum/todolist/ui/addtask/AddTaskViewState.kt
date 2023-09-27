package com.mesum.todolist.ui.addtask

import com.mesum.todolist.redux.state.State

/**
 * implements state that describes the configuration AddTask Screen
 */
data class AddTaskViewState(
    val isEmpty: Boolean = false,
    val isSaved: Boolean = false,
    val title: String = "",
    val description: String = "",
    val dueDate : String? = "",
    val category: String = "",
    val priority : Int = 0,
    val creatingTask : Boolean = false,
    val showProgressBar: Boolean = false,
    val error: Throwable? = null
) : State
