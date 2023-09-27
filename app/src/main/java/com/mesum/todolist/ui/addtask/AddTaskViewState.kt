package com.mesum.todolist.ui.addtask

import com.mesum.todolist.redux.state.State

/**
 * implements state that describes the configuration AddTask Screen
 */
data class AddTaskViewState(
    val isEmpty: Boolean = false,
    val isSaved: Boolean = false,
    val title: String,
    val description: String ,
    val dueDate : String? ,
    val category: String ,
    val priority : Int ,
    val creatingTask : Boolean ,
    val showProgressBar: Boolean ,
    val error: String?
) : State {
    companion object {
        fun idle(): AddTaskViewState {
            return AddTaskViewState(
                title = "",
                description = "",
                error = null,
                isEmpty = false,
                isSaved = false,
                dueDate = "",
                category = "",
                priority = 0,
                creatingTask = false,
                showProgressBar = false,

            )
        }
    }
}
