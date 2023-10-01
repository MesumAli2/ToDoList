package com.mesum.todolist.data.middleware


import com.mesum.todolist.domain.usecase.CreateTaskReminderUseCase
import com.mesum.todolist.domain.usecase.CreateTaskUseCase
import com.mesum.todolist.redux.Middleware
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.createTask.AddTaskViewState
import javax.inject.Inject

class CreatingDatastoreMiddleware @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val createReminderUseCase: CreateTaskReminderUseCase
) : Middleware<AddTaskViewState, TaskAction> {

    override suspend fun process(
        action: TaskAction,
        currentState: AddTaskViewState,
        store: Store<AddTaskViewState, TaskAction>
    ) {
        if (action is TaskAction.CreateTaskButtonClicked) {
            if (validateTaskFields(currentState, store)) {
                createTaskAndReminder(store, currentState)
            }
        }
    }

    private suspend fun validateTaskFields(
        currentState: AddTaskViewState,
        store: Store<AddTaskViewState, TaskAction>
    ): Boolean {
        if (currentState.title.isEmpty()) {
            store.dispatch(TaskAction.InvalidTaskTitle)
            return false
        }
        if (currentState.category.isEmpty() || currentState.category == "Pick Category") {
            store.dispatch(TaskAction.InvalidTaskCategory)
            return false
        }
        if (currentState.description.isEmpty()) {
            store.dispatch(TaskAction.InvalidTaskDescription)
            return false
        }
        if (currentState.priority.isEmpty()) {
            store.dispatch(TaskAction.InvalidTaskPriority)
            return false
        }
        return true
    }

    private suspend fun createTaskAndReminder(
        store: Store<AddTaskViewState, TaskAction>,
        currentState: AddTaskViewState
    ) {
        store.dispatch(TaskAction.TaskCreationStarted)
        val isTaskSuccessful = createTaskUseCase.execute(task = currentState)

        if (isTaskSuccessful) {
            store.dispatch(TaskAction.TaskCreationCompleted)
            createReminder(store, currentState)
        } else {
            store.dispatch(TaskAction.TaskCreationFailed(Error(Throwable())))
        }
    }

    private suspend fun createReminder(
        store: Store<AddTaskViewState, TaskAction>,
        currentState: AddTaskViewState
    ) {
        val isReminderSuccessful = createReminderUseCase.execute(currentState)

        if (isReminderSuccessful) {
            store.dispatch(TaskAction.TaskReminderCreated)
        } else {
            store.dispatch(TaskAction.ReminderFailed)
        }
    }
}
