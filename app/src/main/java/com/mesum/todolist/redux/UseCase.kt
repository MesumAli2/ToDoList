package com.mesum.todolist.redux

abstract class UseCase<I> {
    abstract suspend fun execute(input: I) : Boolean
}
