package com.mesum.todolist.di

import android.content.Context
import com.mesum.todolist.data.middleware.LoggingMiddleware
import com.mesum.todolist.data.repo.TasksRepositoryImpl
import com.mesum.todolist.domain.repository.TasksRepository
import com.mesum.todolist.domain.usecase.DeleteTaskUseCase
import com.mesum.todolist.domain.usecase.MarkTasksCmptUseCase
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.ViewTaskAction
import com.mesum.todolist.ui.reducers.TaskListReducer
import com.mesum.todolist.ui.tasks.TasksListViewState
import com.mesum.todolist.data.middleware.ViewingDatastoreMiddleware
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ViewTaskModule {

    @Provides
    fun provideTaskRepository(@ApplicationContext context: Context): TasksRepository {
        return TasksRepositoryImpl(context)
    }

    @Provides
    fun provideLoadTasksUseCase(@ApplicationContext context: Context): MarkTasksCmptUseCase {
        return MarkTasksCmptUseCase(provideTaskRepository(context))
    }
    @Provides
    fun provideDeleteTaskUseCase(@ApplicationContext context: Context): DeleteTaskUseCase {
        return DeleteTaskUseCase(provideTaskRepository(context))
    }

    @Provides
    fun provideViewTaskStore(
        @ApplicationContext context: Context,
        markTasksCmptUseCase: MarkTasksCmptUseCase,
        deleteTaskUseCase: DeleteTaskUseCase
        ): Store<TasksListViewState, ViewTaskAction> {
        return Store(
            initialState = TasksListViewState.idle(),
            reducer = TaskListReducer(),
            middlewares = listOf(
                LoggingMiddleware(),
                ViewingDatastoreMiddleware(
                    markTasksCmptUseCase = markTasksCmptUseCase,
                    context = context,
                    deleteTaskUseCase = deleteTaskUseCase
                )
            )
        )
    }
}
