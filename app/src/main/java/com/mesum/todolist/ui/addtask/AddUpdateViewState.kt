package com.mesum.todolist.ui.addtask

import com.mesum.todolist.redux.state.State

/**
 * implements state that describes the configuration AddTask Screen
 */
data class AddUpdateViewState(
    val isEmpty: Boolean,
    val isSaved: Boolean,
    val title: String,
    val description: String,
    val dueDate : String?,
    val category: String,
    val priority : Int,
    val creatingTask : Boolean,
    val showProgressBar: Boolean,
    val error: Throwable?
) : State{
    companion object {
        fun idle(): AddUpdateViewState {
            return AddUpdateViewState(
                title = "",
                description = "",
                dueDate = null,
                category = "",
                priority = 0,
                error = null,
                isEmpty = false,
                isSaved = false,
                showProgressBar = false,
                creatingTask = false

            )
        }
    }
}
