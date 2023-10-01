package com.mesum.todolist.data.datastore

import android.content.Context
import androidx.datastore.dataStore

object DataStoreManager {
    /**
     * DataStore property for managing app-specific data.
     *
     * This property provides access to the DataStore, which is used for storing and retrieving
     * app-specific data, such as task state. It's a centralized location for data storage and
     * retrieval within the app.
     *
     * Usage:
     * - Read data: context.dataStore.data
     * - Write data: context.dataStore.edit { /* data modification code */ }
     */
    val Context.dataStore by dataStore("tasks.json", TaskStateSerializer)
}