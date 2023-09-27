package com.mesum.todolist.data

import java.util.UUID

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: String?,
    val category: String,
    val priority: Int,
    val isCompleted: Boolean = false
){
    val titleForList =
        title.ifEmpty {
            description
        }

    val active = !isCompleted

    val empty = title.isEmpty() && description.isEmpty()
}

