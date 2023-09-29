package com.mesum.todolist.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.todolist.CreatingDatastoreMiddleware
import com.mesum.todolist.LoggingMiddleware
import com.mesum.todolist.domain.usecase.CreateTaskUseCase
import com.mesum.todolist.domain.usecase.LoadTasksUseCase
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.action.ViewTaskAction
import com.mesum.todolist.ui.addtask.AddTaskViewState
import com.mesum.todolist.ui.reducer.TaskReducer
import com.mesum.todolist.ui.reducer.ViewTaskReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksViewModel@Inject constructor(
    private val loadTasksUseCase: LoadTasksUseCase
) : ViewModel() {

    private val store = Store(
        initialState = ViewTaskViewState.idle(), // Use ViewTaskViewState for viewing tasks
        reducer = ViewTaskReducer(), // Use ViewTaskReducer for viewing tasks
        middlewares = listOf(
            LoggingMiddleware<ViewTaskViewState, ViewTaskAction>(), // Use ViewTaskViewState and ViewTaskAction here
            ViewingDatastoreMiddleware(
                loadTasksUseCase = loadTasksUseCase
            )
        )
    )

    fun loadTask(newDueDate: String) {
        val action = TaskAction.TaskDueDateChanged(newDueDate)
        viewModelScope.launch {
           // store.dispatch(action)
        }
    }
}