package com.mesum.todolist


import android.content.Context
import com.mesum.todolist.domain.usecase.CreateTaskUseCase
import com.mesum.todolist.redux.Middleware
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.addtask.AddTaskViewState
import dagger.hilt.android.qualifiers.ApplicationContext

class CreatingDatastoreMiddleware(
    private val createTaskUseCase: CreateTaskUseCase,
) : Middleware<AddTaskViewState, TaskAction> {

    override suspend fun process(
        action: TaskAction,
        currentState: AddTaskViewState,
        store: Store<AddTaskViewState, TaskAction>
    ) {
        when (action) {
            is TaskAction.CreateTaskButtonClicked -> {
                if (currentState.title.isEmpty() || currentState.description.isEmpty()) {
                    store.dispatch(TaskAction.InvalidTask)
                    return
                }

                createTask(store, currentState)
            }
            else ->{
            }
        }
    }

    private suspend fun createTask(
        store: Store<AddTaskViewState, TaskAction>,
        currentState: AddTaskViewState
    ) {
        store.dispatch(TaskAction.TaskCreationStarted)

//        val isSuccessful = addTaskRepository.intiTask(
//            title = currentState.title,
//            description = currentState.description
//        )

        val isSuccessful = createTaskUseCase.execute(task = currentState)

        if (isSuccessful) {
            store.dispatch(TaskAction.TaskCreationCompleted)

        } else {
            store.dispatch(TaskAction.TaskCreationFailed(Error(Throwable())))
        }
    }
}
