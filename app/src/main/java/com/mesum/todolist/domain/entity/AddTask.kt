package com.mesum.todolist.domain.entity

import java.util.UUID

data class AddTask  (
    val id: String = UUID.randomUUID().toString(),
    val title: String= "",
    val description: String = "",
    val dueDate: String? = "",
    val category: String = "",
    val priority: Int = 0,
    val isCompleted: Boolean = false
)