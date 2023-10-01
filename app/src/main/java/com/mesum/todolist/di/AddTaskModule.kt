package com.mesum.todolist.di

import android.content.Context
import com.mesum.todolist.data.middleware.CreatingDatastoreMiddleware
import com.mesum.todolist.data.middleware.LoggingMiddleware
import com.mesum.todolist.data.repo.AddTaskRepositoryImpl
import com.mesum.todolist.domain.entitymapper.AddTaskEntityMapper
import com.mesum.todolist.domain.repository.AddTaskRepository
import com.mesum.todolist.domain.usecase.CreateTaskReminderUseCase
import com.mesum.todolist.domain.usecase.CreateTaskUseCase
import com.mesum.todolist.redux.Store
import com.mesum.todolist.ui.action.TaskAction
import com.mesum.todolist.ui.createTask.AddTaskViewState
import com.mesum.todolist.ui.reducers.AddTaskReducer
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
    fun provideCreateReminderUseCase(addTaskRepository: AddTaskRepository): CreateTaskReminderUseCase {
        return CreateTaskReminderUseCase(addTaskRepository)
    }


    @Provides
    fun provideAddTaskStore(@ApplicationContext context: Context): Store<AddTaskViewState, TaskAction> {
        return Store(
            initialState = AddTaskViewState.idle(),
            reducer = AddTaskReducer(),
            middlewares = listOf(
                LoggingMiddleware(),
                CreatingDatastoreMiddleware(createTaskUseCase = provideCreateTaskUseCase(
                    provideAddTaskRepository(context)), createReminderUseCase = provideCreateReminderUseCase(
                    provideAddTaskRepository(context)
                )
                )
            )
        )
    }
}
