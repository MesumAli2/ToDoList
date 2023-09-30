package com.mesum.todolist.ui.tasks

import android.content.Context
import com.mesum.todolist.domain.usecase.DeleteTaskUseCase
import com.mesum.todolist.domain.usecase.MarkTasksCmptUseCase
import com.mesum.todolist.redux.Middleware
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.ViewTaskAction
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
                // Handle filtering tasks by category and dispatch actions accordingly
                // For example, you can dispatch ViewTaskAction.LoadTasks with filtered data
                filterTasksByCategory(action.category, store)
            }

//            is ViewTaskAction.SortTasks -> {
//                // Handle sorting tasks and dispatch actions accordingly
//                // For example, you can dispatch ViewTaskAction.LoadTasks with sorted data
//                sortTasks(action.sortBy, store)
//            }
            is ViewTaskAction.SelectTask -> {
                // Handle selecting a task for detailed view and update the state accordingly
                // For example, you can update the selected task in the state
                // currentState.selectedTaskId = action.taskId
                // Then dispatch a ViewTaskAction.ClearSelectedTask if needed
                // store.dispatch(ViewTaskAction.ClearSelectedTask)
            }
            is ViewTaskAction.DeleteTaskButtonClicked ->{
                startTaskDeletion(store,action.taskId)
            }
            is ViewTaskAction.TaskMarkAsCompleted ->{
                markTasksCmptUseCase.execute(action.taskId)
            }
            // Handle other actions related to viewing tasks here
            else -> {}
        }
    }

    private suspend fun loadTasks(store: Store<ViewTaskViewState, ViewTaskAction>) {
        // Dispatch the loaded tasks to update the state
        store.dispatch(ViewTaskAction.LoadTasksCompleted(context.dataStore.data.firstOrNull()?.tasks ?: mutableListOf()))
    }
    private suspend fun startTaskDeletion(store: Store<ViewTaskViewState, ViewTaskAction>,taskId: String){
        store.dispatch(ViewTaskAction.TaskDeletionStarted)
        val isSuccessful = deleteTaskUseCase.execute(taskId)
        if (isSuccessful){
            store.dispatch(ViewTaskAction.TaskDeletionCompleted(taskId))
        }else store.dispatch(ViewTaskAction.TaskDeletionFailed(Error(Throwable())))
    }

    private suspend fun filterTasksByCategory(
        category: String,
        store: Store<ViewTaskViewState, ViewTaskAction>
    ) {
        // Implement filtering logic and dispatch ViewTaskAction.LoadTasks with filtered data
        // For example:
        // val filteredTasks = loadedTasks.filter { it.category == category }
        // store.dispatch(ViewTaskAction.LoadTasksCompleted(filteredTasks))
    }

//    private suspend fun sortTasks(
//        sortBy: SortBy,
//        store: Store<ViewTaskViewState, ViewTaskAction>
//    ) {
//        // Implement sorting logic and dispatch ViewTaskAction.LoadTasks with sorted data
//        // For example:
//        // val sortedTasks = loadedTasks.sortedBy { it.dueDate }
//        // store.dispatch(ViewTaskAction.LoadTasksCompleted(sortedTasks))
//    }
}
