package com.mesum.todolist.di

import android.content.Context
import com.mesum.todolist.LoggingMiddleware
import com.mesum.todolist.data.TasksRepositoryImpl
import com.mesum.todolist.domain.repository.TasksRepository
import com.mesum.todolist.domain.usecase.LoadTasksUseCase
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.ViewTaskAction
import com.mesum.todolist.ui.reducer.ViewTaskReducer
import com.mesum.todolist.ui.tasks.ViewTaskViewState
import com.mesum.todolist.ui.tasks.ViewingDatastoreMiddleware
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
    fun provideLoadTasksUseCase(@ApplicationContext context: Context): LoadTasksUseCase {
        return LoadTasksUseCase(provideTaskRepository(context))
    }

    @Provides
    fun provideViewTaskStore(
        loadTasksUseCase: LoadTasksUseCase,
        @ApplicationContext context: Context
    ): Store<ViewTaskViewState, ViewTaskAction> {
        return Store(
            initialState = ViewTaskViewState.idle(),
            reducer = ViewTaskReducer(),
            middlewares = listOf(
                LoggingMiddleware(),
                ViewingDatastoreMiddleware(
                    loadTasksUseCase = loadTasksUseCase,
                    context = context
                )
            )
        )
    }
}
