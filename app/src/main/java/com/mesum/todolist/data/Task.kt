package com.mesum.todolist.data

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: String?,
    val category: String,
    val priority: Int,
    val isCompleted: Boolean
)
