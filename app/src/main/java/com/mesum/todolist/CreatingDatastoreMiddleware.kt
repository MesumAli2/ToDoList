package com.mesum.todolist


import android.content.Context
import com.mesum.todolist.domain.usecase.CreateTaskReminderUseCase
import com.mesum.todolist.domain.usecase.CreateTaskUseCase
import com.mesum.todolist.redux.Middleware
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.addtask.AddTaskViewState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreatingDatastoreMiddleware @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val createReminderUseCase: CreateTaskReminderUseCase,

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
            is TaskAction.CreateReminder -> {
                createReminder(store, action.dueDate)
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
        val isSuccessful = createTaskUseCase.execute(task = currentState)
        if (isSuccessful) {
            store.dispatch(TaskAction.TaskCreationCompleted)

        } else {
            store.dispatch(TaskAction.TaskCreationFailed(Error(Throwable())))
        }
    }

    private suspend fun createReminder(
        store: Store<AddTaskViewState, TaskAction>,
        dueDate: String
    ){
        val isSuccessful = createReminderUseCase.execute(dueDate)
        if (isSuccessful){
            store.dispatch(TaskAction.TaskReminderCreated)
        }else{
            store.dispatch(TaskAction.ReminderFailed)
        }
    }


}
