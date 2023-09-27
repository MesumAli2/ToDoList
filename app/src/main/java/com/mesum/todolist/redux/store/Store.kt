package com.mesum.todolist.redux.store

import com.mesum.todolist.redux.action.Action
import com.mesum.todolist.redux.reducer.Reducer
import com.mesum.todolist.redux.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A store is a place to store the state of a given screen.
 *
 * @param[initialState]Intial state of screen when its is first created.
 * @param[reducer] Takes current state, and a new action, and outputting the
 * updated state.
 */
class Store<S : State, A: Action> (
    initialState : S,
   private val reducer: Reducer<S, A>
        ){

    private val _state = MutableStateFlow(initialState)
    val state : StateFlow<S> = _state

    fun dispatch(action: A) {
        val currentState = _state.value
        val newState = reducer.reduce(currentState, action)
        _state.value =  newState
    }
}