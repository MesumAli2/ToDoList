package com.mesum.todolist.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mesum.todolist.R
import com.mesum.todolist.data.Task
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class TaskSection(
    private val category: String,
    private val tasks: List<Task>,
    private val onClickCmptTask: (Task) -> Unit,
    private val onDeleteTask: (Task) -> Unit
) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.item_task) // Layout for task items
        .headerResourceId(R.layout.item_category) // Layout for category headers
        .build()
) {
    override fun getContentItemsTotal(): Int {
        return tasks.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return TaskListAdapter.TaskViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val taskViewHolder = holder as TaskListAdapter.TaskViewHolder
        taskViewHolder.bind(tasks[position], onDeleteTask = onDeleteTask, onTaskClick = onClickCmptTask)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return TaskListAdapter.CategoryViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        val categoryViewHolder = holder as TaskListAdapter.CategoryViewHolder
        categoryViewHolder.bind(category)
    }
}
