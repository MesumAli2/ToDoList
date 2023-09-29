package com.mesum.todolist

import android.util.Log
import com.mesum.todolist.redux.Action
import com.mesum.todolist.redux.Middleware
import com.mesum.todolist.redux.State
import com.mesum.todolist.redux.Store

/**
 * This [Middleware] is responsible for logging every [Action] that is processed to the Logcat, so
 * that we can use this for debugging.
 */
class LoggingMiddleware<S: State, A: Action> : Middleware<S, A> {
    override suspend fun process(action: A, currentState: S, store: Store<S, A>) {
        Log.v(
            "LoggingMiddleware",
            "Processing action: $action; Current state: $currentState"
        )
    }
}