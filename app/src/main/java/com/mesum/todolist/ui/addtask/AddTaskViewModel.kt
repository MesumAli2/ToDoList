package com.mesum.todolist.ui.addtask

import androidx.lifecycle.ViewModel
import com.mesum.todolist.redux.store.Store
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.reducer. TaskReducer
import kotlinx.coroutines.flow.StateFlow

/**
 * The [AddTaskViewModel] is responsible for controlling the UI logic of the add task screen. It will
 * listen for text changes and button clicks, and update the UI state accordingly and expose that so the View can update.
 */
class AddTaskViewModel : ViewModel() {
    private val store = Store(
        initialState = AddTaskViewState(),
        reducer = TaskReducer(),
    )

    val viewState: StateFlow<AddTaskViewState> = store.state

    fun taskTitleChanged(newTitle: String) {
        val action = TaskAction.TaskTitleChanged(newTitle)
        store.dispatch(action)
    }

    fun taskDescriptionChanged(newDescription: String) {
        val action = TaskAction.TaskDescriptionChanged(newDescription)
        store.dispatch(action)
    }

    fun taskDueDateChanged(newDueDate: String) {
        val action = TaskAction.TaskDueDateChanged(newDueDate)
        store.dispatch(action)
    }

    fun taskCategoryChanged(newCategory: String) {
        val action = TaskAction.TaskCategoryChanged(newCategory)
        store.dispatch(action)
    }

    fun taskPriorityChanged(newPriority: Int) {
        val action = TaskAction.TaskPriorityChanged(newPriority)
        store.dispatch(action)
    }

    fun createTaskButtonClicked() {
        val action = TaskAction.CreateTaskButtonClicked
        store.dispatch(action)
    }

    // You can define the other action methods in a similar manner

}
