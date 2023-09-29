package com.mesum.todolist.data.local

import androidx.datastore.core.Serializer
import com.mesum.todolist.data.Tasks
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

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

