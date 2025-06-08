package com.example.smartlist

data class Todo(
    var id: Long = 0,
    var title: String,
    var isCompleted: Boolean = false
) 