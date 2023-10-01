package com.mesum.todolist.ui.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mesum.todolist.R // Replace with your package name
import com.mesum.todolist.data.Task
import com.mesum.todolist.util.convertTime

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val completeCheckBox: CheckBox = itemView.findViewById(R.id.completeCheckBox)
        private val deleteTaskButton: ImageView = itemView.findViewById(R.id.deleteImageView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val timeLinearLayout: LinearLayout = itemView.findViewById(R.id.time_ll)
        private val dataLinearLayout: LinearLayout = itemView.findViewById(R.id.date_ll)

        private val priorityTextView: TextView = itemView.findViewById(R.id.priorityTextView)
        fun bind(task: Task, onTaskClick: (Task) -> Unit, onDeleteTask: (Task) -> Unit) {
            titleTextView.text = task.title
            descriptionTextView.text = task.description
            priorityTextView.text = task.priority
            if (task.dueDate?.isEmpty()== true){
                dataLinearLayout.visibility = View.GONE

            }else{
                dataLinearLayout.visibility = View.VISIBLE
                dateTextView.text = task.dueDate
            }
            if (task.time?.isEmpty() == true){
                timeLinearLayout.visibility = View.GONE
            }else {
                timeLinearLayout.visibility = View.VISIBLE
                timeTextView.text = itemView.context.convertTime(task.time.toString())
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

