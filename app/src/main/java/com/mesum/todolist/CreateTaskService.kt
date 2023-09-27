package com.mesum.todolist

import kotlinx.coroutines.delay

class CreateTaskService : CreateTaskRepository {

    override suspend fun intiTask(title: String, description: String): Boolean {
        delay(2000)

        return true
    }
}
