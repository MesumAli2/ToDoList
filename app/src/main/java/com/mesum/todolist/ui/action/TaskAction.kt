package com.mesum.todolist.ui.action

import com.mesum.todolist.redux.action.Action

/**
 *  Action that can be tri ggered from the AddTaskScreen
 */
sealed class TaskAction : Action {
    data class TaskTitleChanged(val newTitle: String) : TaskAction()
    data class TaskDescriptionChanged(val newDescription: String) : TaskAction()
    data class TaskDueDateChanged(val newDueDate: String) : TaskAction()
    data class TaskCategoryChanged(val newCategory: String) : TaskAction()
    data class TaskPriorityChanged(val newPriority: Int) : TaskAction()

    // For task creation
    object CreateTaskButtonClicked : TaskAction()
    object TaskCreationStarted : TaskAction()
    object TaskCreationCompleted : TaskAction()
    data class TaskCreationFailed(val error: Throwable?) : TaskAction()

    // For task updating
    data class UpdateTaskButtonClicked(val taskId: String) : TaskAction()
    object TaskUpdateStarted : TaskAction()
    object TaskUpdateCompleted : TaskAction()
    data class TaskUpdateFailed(val error: Throwable?) : TaskAction()

    // For task deletion
    data class DeleteTaskButtonClicked(val taskId: String) : TaskAction()
    object TaskDeletionStarted : TaskAction()
    object TaskDeletionCompleted : TaskAction()
    data class TaskDeletionFailed(val error: Throwable?) : TaskAction()
}
