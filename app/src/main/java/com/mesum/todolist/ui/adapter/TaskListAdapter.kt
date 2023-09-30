package com.mesum.todolist.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mesum.todolist.R // Replace with your package name
import com.mesum.todolist.data.Task

class TaskListAdapter(private val onClickCmptTask: (Task) -> Unit, private val onDeleteTask: (Task) -> Unit) :
    ListAdapter<Task, RecyclerView.ViewHolder>(TaskDiffCallback()) {
    private val CATEGORY_VIEW_TYPE = 0
    private val TASK_VIEW_TYPE = 1
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textDescription)
        private val completeCheckBox: CheckBox = itemView.findViewById(R.id.complete_checkBox)
        private val deleteTaskButton: ImageView = itemView.findViewById(R.id.delete_iv)
        private val taskCategoryTextView: TextView = itemView.findViewById(R.id.task_category_tv)
        fun bind(task: Task, onTaskClick: (Task) -> Unit, onDeleteTask: (Task) -> Unit) {
            titleTextView.text = task.title
            descriptionTextView.text = task.description
            taskCategoryTextView.text = buildString { append("Category: ")
                append(task.category)
            }
            // Bind other task properties as needed
            completeCheckBox.isChecked = task.isCompleted
            completeCheckBox.setOnClickListener {
                onTaskClick(task)
            }
            deleteTaskButton.setOnClickListener {
                onDeleteTask(task)
            }
        }

    }

     class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)

        fun bind(category: String) {
            categoryTextView.text = category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CATEGORY_VIEW_TYPE -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category, parent, false) // Use the new layout file for categories
                CategoryViewHolder(itemView)
            }
            TASK_VIEW_TYPE -> {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
                TaskViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CATEGORY_VIEW_TYPE -> {
                val categoryViewHolder = holder as CategoryViewHolder
                val category = getItem(position).category as String
                categoryViewHolder.bind(category)
            }
            TASK_VIEW_TYPE -> {
                val taskViewHolder = holder as TaskViewHolder
                val task = getItem(position) as Task
                taskViewHolder.bind(task, onClickCmptTask, onDeleteTask)
            }
        }
    }
//    override fun getItemViewType(position: Int): Int {
//        return if (getItem(position) ) {
//            CATEGORY_VIEW_TYPE
//        } else {
//            TASK_VIEW_TYPE
//        }
//    }


    private fun isCategory(task: Task): Boolean {
        val predefinedCategories = setOf("Work", "Personal", "Shopping", "Wishlist", "Outing")
        return task.category in predefinedCategories
    }

}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
