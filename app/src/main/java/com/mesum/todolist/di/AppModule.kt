package com.mesum.todolist.di

import android.app.Application
import android.content.Context
import com.mesum.todolist.data.AddTaskRepositoryImpl
import com.mesum.todolist.domain.entitymapper.AddTaskEntityMapper
import com.mesum.todolist.domain.repository.AddTaskRepository
import com.mesum.todolist.domain.usecase.CreateTaskUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



//    @Provides
//    fun provideTaskDao(db: TaskDatabase) = db.taskDao()
//
//    @ApplicationScope
//    @Provides
//    @Singleton
//    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

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
        // You can use 'context' here as needed in AddTaskRepositoryImpl
        return AddTaskRepositoryImpl(context, provideAddTaskEntityMapper())
    }

    @Provides
    @Singleton
    fun provideCreateTaskUseCase(addTaskRepository: AddTaskRepository): CreateTaskUseCase {
        return CreateTaskUseCase(addTaskRepository)
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope