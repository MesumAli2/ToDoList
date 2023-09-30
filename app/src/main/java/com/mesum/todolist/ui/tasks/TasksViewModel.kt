package com.mesum.todolist.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.ViewTaskAction
import dagger.hilt.android.lifecycle.HiltViewModel
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
    fun deleteTask(taskId: String){
        val action = ViewTaskAction.DeleteTaskButtonClicked(taskId)
        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun searchTasks(query: String) {
        val action = ViewTaskAction.SearchQuery(query)
        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun categorySelected(selectedCategory: String) {
        val action = ViewTaskAction.FilterTasksByCategory(selectedCategory)
        viewModelScope.launch {
            store.dispatch(action)
        }

    }

    fun sortTasks(sortBy: String) {
        val action = ViewTaskAction.SortTasks(sortBy)
        viewModelScope.launch{
            store.dispatch(action)
    }
    }


}