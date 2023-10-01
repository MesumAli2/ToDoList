package com.mesum.todolist.ui.addtask

import com.mesum.todolist.redux.State
import java.util.UUID

/**
 * implements state that describes the configuration AddTask Screen
 */
data class AddTaskViewState(
    val id : String = "",
    val isEmpty: Boolean,
    val isSaved: Boolean,
    val title: String,
    val description: String,
    val dueDate : String?,
    val time: String?,
    val category: String,
    val priority : String,
    val creatingTask : Boolean,
    val showProgressBar: Boolean,
    val isCompleted: Boolean,
    val errorTitle: String?,
    val errorDescription: String?,
    val errorPriority: String?,
    val errorCategory: String?,
    val taskAdded : Boolean
) : State {
    companion object {
        fun idle(): AddTaskViewState {
            return AddTaskViewState(
                id = UUID.randomUUID().toString(),
                title = "",
                description = "",
                errorTitle = null,
                errorDescription = null,
                errorPriority = null,
                errorCategory = null,
                isEmpty = false,
                isSaved = false,
                dueDate = "",
                category = "",
                priority = "",
                creatingTask = false,
                showProgressBar = false,
                isCompleted = false,
                taskAdded = false,
                time = ""

            )
        }
    }
}
