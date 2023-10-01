package com.mesum.todolist.ui.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.todolist.domain.usecase.CreateTaskUseCase
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.TaskAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The [AddTaskViewModel] is responsible for controlling the UI logic of the add task screen. It will
 * listen for text changes and button clicks, and update the UI state accordingly and expose that so the View can update.
 *
 * Proxy corresponding [TaskAction] to the store whenever a view action occurs, such as [taskTitleChanged]
 * or [createTaskButtonClicked].
 */
@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val store: Store<AddTaskViewState, TaskAction>
) : ViewModel() {


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

    fun taskPriorityChanged(newPriority: String) {
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

    fun takTimeDateChanged(selectedTime: String) {
        val action = TaskAction.TaskTimeChanged(selectedTime)
        viewModelScope.launch {
            store.dispatch(action)
        }

    }


}
