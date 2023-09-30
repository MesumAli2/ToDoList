package com.mesum.todolist.ui.tasks

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.todolist.CreatingDatastoreMiddleware
import com.mesum.todolist.LoggingMiddleware
import com.mesum.todolist.Task
import com.mesum.todolist.domain.usecase.CreateTaskUseCase
import com.mesum.todolist.domain.usecase.LoadTasksUseCase
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.action.ViewTaskAction
import com.mesum.todolist.ui.addtask.AddTaskViewState
import com.mesum.todolist.ui.reducer.TaskReducer
import com.mesum.todolist.ui.reducer.ViewTaskReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val store: Store<ViewTaskViewState, ViewTaskAction>,
) : ViewModel() {
    init {
        startLoadingTask()
    }
    val viewState = store.state

    private  fun startLoadingTask() {
        val action = ViewTaskAction.LoadTasksStarted
        viewModelScope.launch {
           store.dispatch(action)
        }
    }

    fun markTaskAsCompleted(taskId : String){
        val action = ViewTaskAction.TaskMarkAsCompleted(taskId)
        viewModelScope.launch {
            store.dispatch(action)
        }
    }


}