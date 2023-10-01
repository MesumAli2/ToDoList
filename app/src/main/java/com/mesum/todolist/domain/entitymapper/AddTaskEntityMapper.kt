package com.mesum.todolist.domain.entitymapper

import com.mesum.todolist.data.Task
import com.mesum.todolist.data.Tasks
import com.mesum.todolist.domain.entity.AddTask
import com.mesum.todolist.redux.Mapper
import com.mesum.todolist.ui.addtask.AddTaskViewState
import javax.inject.Inject

class AddTaskEntityMapper @Inject constructor() : Mapper<AddTaskViewState, Task> {
    override fun map(left: AddTaskViewState): Task {
        return Task(
            id = left.id,
            title = left.title,
            description = left.description,
            dueDate = left.dueDate,
            category = left.category,
            priority = left.priority,
            isCompleted = left.isCompleted,
            time = left.time
        )
    }
}