package com.mesum.todolist.redux

abstract class UseCase<I, O> {
    abstract suspend fun execute(input: I): O
}
