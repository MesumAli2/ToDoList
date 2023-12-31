package com.mesum.todolist.ui.action

import com.mesum.todolist.redux.Action

/**
 *  Action that can be triggered from the AddTaskScreen
 */
sealed class TaskAction : Action {
    data class TaskTitleChanged(val newTitle: String) : TaskAction()
    data class TaskDescriptionChanged(val newDescription: String) : TaskAction()
    data class TaskDueDateChanged(val newDueDate: String) : TaskAction()
    data class TaskTimeChanged(val newTime: String) : TaskAction()
    data class TaskCategoryChanged(val newCategory: String) : TaskAction()
    data class TaskPriorityChanged(val newPriority: String) : TaskAction()
    // For task creation
    object CreateTaskButtonClicked : TaskAction()
    object TaskCreationStarted : TaskAction()
    object TaskCreationCompleted : TaskAction()
    object InvalidTaskTitle : TaskAction()
    object InvalidTaskDescription : TaskAction()
    object InvalidTaskPriority : TaskAction()
    object InvalidTaskCategory : TaskAction()

    data class TaskCreationFailed(val error: Throwable?) : TaskAction()
    object TaskReminderCreated : TaskAction()
    object ReminderFailed : TaskAction()




}
