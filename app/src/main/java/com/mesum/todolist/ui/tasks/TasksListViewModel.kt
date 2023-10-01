package com.mesum.todolist.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.ViewTaskAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val store: Store<TasksListViewState, ViewTaskAction>
) : ViewModel() {

    val viewState = store.state

    init {
        startLoadingTasks()
    }

    private fun dispatchAction(action: ViewTaskAction) {
        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    private fun startLoadingTasks() {
        dispatchAction(ViewTaskAction.LoadTasksStarted)
    }

    fun markTaskAsCompleted(taskId: String) {
        dispatchAction(ViewTaskAction.TaskMarkAsCompleted(taskId))
    }

    fun deleteTask(taskId: String) {
        dispatchAction(ViewTaskAction.DeleteTaskButtonClicked(taskId))
    }

    fun searchTasks(query: String) {
        dispatchAction(ViewTaskAction.SearchQuery(query))
    }

    fun categorySelected(selectedCategory: String) {
        dispatchAction(ViewTaskAction.FilterTasksByCategory(selectedCategory))
    }

    fun sortTasks(sortBy: String) {
        dispatchAction(ViewTaskAction.SortTasks(sortBy))
    }
}
