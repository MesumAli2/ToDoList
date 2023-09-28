package com.mesum.todolist

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import java.util.prefs.Preferences

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "task")
//
//class TaskRepository(private val context: Context) {
//
//    private val dataStore = context.dataStore
//
//    val taskFlow: Flow<Task?> = dataStore.data
//        .map { preferences ->
//            val taskData = preferences[PreferenceKeys.TASK_KEY]
//            taskData?.let { protoData ->
//                Task.parseFrom(protoData)
//            }
//        }
//
//    suspend fun saveTask(task: Task) {
//        dataStore.edit { preferences ->
//            preferences[PreferenceKeys.TASK_KEY] = task.toByteString()
//        }
//    }
//
//    object PreferenceKeys {
//        val TASK_KEY = preferencesKey<ByteArray>("task_key") {
//            defaultValue(byteArrayOf())
//            serializer = ProtoSerializer(Task.getDefaultInstance())
//        }
//    }
//}

