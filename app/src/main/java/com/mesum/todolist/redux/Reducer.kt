package com.mesum.todolist.redux

/**
 * Predict the next state of the system based on the current state and the user's action.
 *
 * This approach will allow us to manage the state of our application in a clear and predictable way, ensuring that each state is associated
 * with a specific user intent or action.
 */
interface Reducer <S : State, A: Action> {

    fun reduce(currentState : S, action : A) : S
}