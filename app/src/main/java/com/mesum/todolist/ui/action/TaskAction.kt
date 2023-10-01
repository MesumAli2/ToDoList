package com.mesum.todolist.ui.action

import com.mesum.todolist.redux.Action

/**
 *  Action that can be tri ggered from the AddTaskScreen
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
    object InvalidTask : TaskAction()
    data class TaskCreationFailed(val error: Throwable?) : TaskAction()
    data class CreateReminder(val dueDate: String) : TaskAction()
    object TaskReminderCreated : TaskAction()

    object ReminderFailed : TaskAction()



//    // For task updating
//    data class UpdateTaskButtonClicked(val taskId: String) : TaskAction()
//    object TaskUpdateStarted : TaskAction()
//    data class TaskUpdateFailed(val error: Throwable?) : TaskAction()
//

}
