package com.mesum.todolist.redux

/**
 * A [Middleware] is any class that deals with side effects of actions, such as logging or
 * interacting with the Android data store.
 */

interface Middleware<S: State, A: Action> {
    /**
     * This middleware will process the given [action] and [currentState] to determine if any side
     * effects need to be performed, or if a new action needs to be triggered..
     */
    suspend fun process(
        action: A,
        currentState: S,
        store: Store<S, A>,
    )
}
