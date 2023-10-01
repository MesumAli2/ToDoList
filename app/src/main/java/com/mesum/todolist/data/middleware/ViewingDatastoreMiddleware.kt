package com.mesum.todolist.data.middleware

import android.content.Context
import com.mesum.todolist.data.Task
import com.mesum.todolist.domain.usecase.DeleteTaskUseCase
import com.mesum.todolist.domain.usecase.MarkTasksCmptUseCase
import com.mesum.todolist.redux.Middleware
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.ViewTaskAction
import com.mesum.todolist.ui.tasks.ViewTaskViewState
import com.mesum.todolist.util.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ViewingDatastoreMiddleware @Inject constructor(
@ApplicationContext private val context: Context,
private val markTasksCmptUseCase: MarkTasksCmptUseCase,
private val deleteTaskUseCase: DeleteTaskUseCase
// Other dependencies as needed
) : Middleware<ViewTaskViewState, ViewTaskAction> {

    override suspend fun process(
        action: ViewTaskAction,
        currentState: ViewTaskViewState,
        store: Store<ViewTaskViewState, ViewTaskAction>
    ) {
        when (action) {
            is ViewTaskAction.LoadTasksStarted -> {
                loadTasks(store)
            }
            is ViewTaskAction.FilterTasksByCategory -> {
                filterTasksByCategory(action.category, store)
            }
            is ViewTaskAction.SearchQuery -> {
                searchTasks(store, action.query)
            }
            is ViewTaskAction.SelectTask -> {
                // Handle selecting a task for detailed view and update the state accordingly
                // For example, you can update the selected task in the state
                // currentState.selectedTaskId = action.taskId
                // Then dispatch a ViewTaskAction.ClearSelectedTask if needed
                // store.dispatch(ViewTaskAction.ClearSelectedTask)
            }
            is ViewTaskAction.TaskMarkAsCompleted -> {
                markTasksAsCompleted(action.taskId)
            }
            is ViewTaskAction.DeleteTaskButtonClicked -> {
                deleteTask(store, action.taskId)
            }

            is ViewTaskAction.SortTasks -> {
                sortTasks(action.sortBy, store)
            }
            // Handle other actions related to viewing tasks here
            else -> {}
        }
    }

    private suspend fun loadTasks(store: Store<ViewTaskViewState, ViewTaskAction>) {
        val tasks = context.dataStore.data.firstOrNull()?.tasks ?: mutableListOf()
        store.dispatch(ViewTaskAction.LoadTasksCompleted(tasks))
    }

    private suspend fun deleteTask(store: Store<ViewTaskViewState, ViewTaskAction>, taskId: String) {
        store.dispatch(ViewTaskAction.TaskDeletionStarted)
        val isSuccessful = deleteTaskUseCase.execute(taskId)
        if (isSuccessful) {
            store.dispatch(ViewTaskAction.TaskDeletionCompleted(taskId))
        } else {
            store.dispatch(ViewTaskAction.TaskDeletionFailed(Error(Throwable())))
        }
    }

    private suspend fun searchTasks(store: Store<ViewTaskViewState, ViewTaskAction>, query: String) {
        val tasks = context.dataStore.data.firstOrNull()?.tasks ?: mutableListOf()
        val filteredTasks = tasks.filter { task ->
            task.title.contains(query, ignoreCase = true) ||
                    task.description.contains(query, ignoreCase = true)
        }
        store.dispatch(ViewTaskAction.SearchQueryCompleted(searchedTasks = filteredTasks))
    }

    private suspend fun filterTasksByCategory(category: String, store: Store<ViewTaskViewState, ViewTaskAction>) {
        val tasks = context.dataStore.data.firstOrNull()?.tasks ?: emptyList()
        val filteredTasks = if (category == "All List") {
            tasks
        } else {
            tasks.filter { it.category == category }
        }
        store.dispatch(ViewTaskAction.TasksFiltered(filteredTasks))
    }



    private suspend fun sortTasks(sortBy: String, store: Store<ViewTaskViewState, ViewTaskAction>) {
        val tasks = context.dataStore.data.firstOrNull()?.tasks ?: mutableListOf()

        val sortedTasks: List<Task> = when (sortBy) {
            "Priority" -> {
                tasks.sortedByDescending { it.priority }
            }
            "Due Date" -> tasks.sortedByDescending { it.dueDate }
            "Completion" -> {
                tasks.sortedBy { it.isCompleted }
            }
            else -> tasks // Default to original order if sortBy is not recognized
        }

        store.dispatch(ViewTaskAction.TasksSorted(sortedTasks))
    }

    private suspend fun markTasksAsCompleted(taskId: String){
       markTasksCmptUseCase.execute(taskId)
    }

    fun sortByIsCompleted(tasks: List<Task>): List<Task> {
        val completedTasks = mutableListOf<Task>()
        val incompleteTasks = mutableListOf<Task>()

        for (task in tasks) {
            if (task.isCompleted) {
                completedTasks.add(task)
            } else {
                incompleteTasks.add(task)
            }
        }

        return (completedTasks + incompleteTasks).sortedByDescending { it.isCompleted }
    }
}


