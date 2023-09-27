package com.mesum.todolist.redux.store

import com.mesum.todolist.redux.action.Action
import com.mesum.todolist.redux.middleware.Middleware
import com.mesum.todolist.redux.reducer.Reducer
import com.mesum.todolist.redux.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A store is a place to store the state of a given screen.
 *
 * @param[initialState]Initial state of screen when its is first created.
 * @param[reducer] Takes current state, and a new action, and outputting the
 * updated state.
 * @param[middlewares] Middleware handles any side effects dispatched to the store.
 */
class Store<S : State, A: Action> (
    initialState : S,
    private val reducer: Reducer<S, A>,
    private val middlewares: List<Middleware<S, A>> = emptyList(),

    ){

    private val _state = MutableStateFlow(initialState)
    val state : StateFlow<S> = _state
    private val currentState: S
        get() = _state.value

    suspend fun dispatch(action: A) {

        middlewares.forEach { middleware ->
            middleware.process(action, currentState, this)
        }
        val newState = reducer.reduce(currentState, action)
        _state.value =  newState
    }
}