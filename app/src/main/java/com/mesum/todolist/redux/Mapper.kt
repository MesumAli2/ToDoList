package com.mesum.todolist.redux

interface Mapper<L, R> {
    fun map(left: L): R
}
