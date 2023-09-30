package com.mesum.todolist.ui.adapter

import com.mesum.todolist.data.Task
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

class TaskSectionAdapter(
    private val onClickCmptTask: (Task) -> Unit,
    private val onDeleteTask: (Task) -> Unit
) : SectionedRecyclerViewAdapter() {

    fun setTasks(tasks: List<Task>) {
        // Clear existing sections
        removeAllSections()

        // Group tasks by category
        val groupedTasks = tasks.groupBy { it.category }

        for ((category, categoryTasks) in groupedTasks) {
            val section = TaskSection(category, categoryTasks, onDeleteTask = onDeleteTask, onClickCmptTask = onClickCmptTask)
            addSection(section)
        }
    }

}