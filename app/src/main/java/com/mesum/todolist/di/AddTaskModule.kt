package com.mesum.todolist.di

import android.content.Context
import com.mesum.todolist.CreatingDatastoreMiddleware
import com.mesum.todolist.LoggingMiddleware
import com.mesum.todolist.data.AddTaskRepositoryImpl
import com.mesum.todolist.domain.entitymapper.AddTaskEntityMapper
import com.mesum.todolist.domain.repository.AddTaskRepository
import com.mesum.todolist.domain.usecase.CreateTaskUseCase
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.addtask.AddTaskViewState
import com.mesum.todolist.ui.reducer.TaskReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AddTaskModule {

    @Provides
    @Singleton
    fun provideAddTaskEntityMapper(): AddTaskEntityMapper {
        return AddTaskEntityMapper()
    }

    @Provides
    @Singleton
    fun provideAddTaskRepository(
        @ApplicationContext context: Context
    ): AddTaskRepository {
        return AddTaskRepositoryImpl(context, provideAddTaskEntityMapper())
    }

    @Provides
    @Singleton
    fun provideCreateTaskUseCase(addTaskRepository: AddTaskRepository): CreateTaskUseCase {
        return CreateTaskUseCase(addTaskRepository)
    }

    @Provides
    @Singleton
    fun provideAddTaskStore(@ApplicationContext context: Context): Store<AddTaskViewState, TaskAction> {
        return Store(
            initialState = AddTaskViewState.idle(),
            reducer = TaskReducer(),
            middlewares = listOf(
                LoggingMiddleware(),
                CreatingDatastoreMiddleware(createTaskUseCase = provideCreateTaskUseCase(
                    provideAddTaskRepository(context)))
            )
        )
    }
}
