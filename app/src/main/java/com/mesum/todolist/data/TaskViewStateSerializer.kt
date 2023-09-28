package com.mesum.todolist.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.mesum.todolist.TaskViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object TaskViewStateSerializer : Serializer<TaskViewState> {
    override val defaultValue: TaskViewState = TaskViewState.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): TaskViewState {
        try {
            return  TaskViewState.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TaskViewState, output: OutputStream) = t.writeTo(output)
}

val Context.appStartUpParamsDataStore: DataStore<TaskViewState> by dataStore(
  fileName =  "tasks_params.pb",
   serializer =  TaskViewStateSerializer
)


class TaskViewStateDataStore(
    private val dataStore: DataStore<TaskViewState>
) {
    val taskViewStateFlow: Flow<TaskViewState> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(TaskViewState.getDefaultInstance())
            } else {
                throw exception
            }
        }
        .map { it }

    suspend fun saveTaskViewState(taskViewState: TaskViewState) {
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .mergeFrom(taskViewState)
                .build()
        }
    }
}



object TaskStateSerializer : Serializer<Tasks>{
    override val defaultValue: Tasks
        get() = Tasks()

    override suspend fun readFrom(input: InputStream): Tasks {
        return try {
            Json.decodeFromString (
                deserializer = Tasks.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace ()
            defaultValue

    }}

    override suspend fun writeTo(t: Tasks, output: OutputStream) = output.write(
            Json.encodeToString(
                serializer = Tasks.serializer(),
                value = t
            ).encodeToByteArray()
        )

}

