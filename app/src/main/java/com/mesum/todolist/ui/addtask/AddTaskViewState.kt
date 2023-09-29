package com.mesum.todolist.ui.addtask

import com.mesum.todolist.redux.State
import java.util.UUID

/**
 * implements state that describes the configuration AddTask Screen
 */
data class AddTaskViewState(
    val id : String = "",
    val isEmpty: Boolean ,
    val isSaved: Boolean ,
    val title: String,
    val description: String ,
    val dueDate : String? ,
    val category: String ,
    val priority : Int ,
    val creatingTask : Boolean ,
    val showProgressBar: Boolean ,
    val isCompleted: Boolean,
    val error: String?
) : State {
    companion object {
        fun idle(): AddTaskViewState {
            return AddTaskViewState(
                id = "",
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
                isCompleted = false

            )
        }
    }
}
