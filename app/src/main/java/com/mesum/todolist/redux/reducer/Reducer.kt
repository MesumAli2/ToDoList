package com.mesum.todolist.redux.reducer

import com.mesum.todolist.redux.action.Action
import com.mesum.todolist.redux.state.State

/**
 * Predict the next state of the system based on the current state and the user's action.

 */
interface Reducer {
    fun reduce(currentState : State, action : Action) : State
}