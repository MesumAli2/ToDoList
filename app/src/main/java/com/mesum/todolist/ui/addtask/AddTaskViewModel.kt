package com.mesum.todolist.ui.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.todolist.CreateTaskRepository
import com.mesum.todolist.CreateTaskService
import com.mesum.todolist.CreatingDatastoreMiddleware
import com.mesum.todolist.LoggingMiddleware
import com.mesum.todolist.redux.store.Store
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.reducer. TaskReducer
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * The [AddTaskViewModel] is responsible for controlling the UI logic of the add task screen. It will
 * listen for text changes and button clicks, and update the UI state accordingly and expose that so the View can update.
 *
 * Proxy corresponding [TaskAction] to the store whenever a view action occurs, such as [taskTitleChanged]
 * or [createTaskButtonClicked].
 */
class AddTaskViewModel : ViewModel() {
    private val store = Store(
        initialState = AddTaskViewState.idle(),
        reducer = TaskReducer(),
        middlewares = listOf(
            LoggingMiddleware(),
            CreatingDatastoreMiddleware(
                addTaskRepository = CreateTaskService()
            )
        )
    )

    val viewState: StateFlow<AddTaskViewState> = store.state

    fun taskTitleChanged(newTitle: String) {
        val action = TaskAction.TaskTitleChanged(newTitle)
        viewModelScope.launch {
            store.dispatch(action)
        }

    }

    fun taskDescriptionChanged(newDescription: String) {
        val action = TaskAction.TaskDescriptionChanged(newDescription)
        viewModelScope.launch {
        store.dispatch(action)
        }
    }

    fun taskDueDateChanged(newDueDate: String) {
        val action = TaskAction.TaskDueDateChanged(newDueDate)
        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun taskCategoryChanged(newCategory: String) {
        val action = TaskAction.TaskCategoryChanged(newCategory)
        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun taskPriorityChanged(newPriority: Int) {
        val action = TaskAction.TaskPriorityChanged(newPriority)
        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun createTaskButtonClicked() {
        val action = TaskAction.CreateTaskButtonClicked
        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    // You can define the other action methods in a similar manner

}
