package com.mesum.todolist.data

import kotlinx.serialization.Serializable
import java.util.UUID


@Serializable
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String= "",
    val description: String = "",
    val dueDate: String? = "",
    val time: String? = "",
    val category: String = "",
    val priority: String = "",
    val isCompleted: Boolean = false
)



@Serializable
data class Tasks(
    val tasks : MutableList<Task> = mutableListOf()
)


