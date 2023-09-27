package com.mesum.todolist

interface CreateTaskRepository {
    suspend fun intiTask(title: String, description: String) : Boolean
}