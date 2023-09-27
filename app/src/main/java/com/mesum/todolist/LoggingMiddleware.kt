package com.mesum.todolist

import android.util.Log
import com.mesum.todolist.redux.action.Action
import com.mesum.todolist.redux.middleware.Middleware
import com.mesum.todolist.redux.state.State
import com.mesum.todolist.redux.store.Store

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