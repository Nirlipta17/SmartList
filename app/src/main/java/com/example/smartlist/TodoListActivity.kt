package com.example.smartlist

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListActivity : AppCompatActivity() {
    private lateinit var adapter: TodoAdapter
    private val todos = mutableListOf<Todo>()
    private var currentId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        setupRecyclerView()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(
            todos,
            onEditClick = { todo -> showEditDialog(todo) },
            onDeleteClick = { todo -> deleteTodo(todo) },
            onTodoChecked = { todo, isChecked -> updateTodoStatus(todo, isChecked) }
        )

        findViewById<RecyclerView>(R.id.todoRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@TodoListActivity)
            adapter = this@TodoListActivity.adapter
        }
    }

    private fun setupAddButton() {
        findViewById<FloatingActionButton>(R.id.fabAddTodo).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val editText = EditText(this).apply {
            hint = "Enter todo item"
            setPadding(32, 32, 32, 32)
        }

        AlertDialog.Builder(this)
            .setTitle("Add Todo")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val todoText = editText.text.toString()
                if (todoText.isNotEmpty()) {
                    addTodo(todoText)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditDialog(todo: Todo) {
        val editText = EditText(this).apply {
            setText(todo.title)
            setPadding(32, 32, 32, 32)
        }

        AlertDialog.Builder(this)
            .setTitle("Edit Todo")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val todoText = editText.text.toString()
                if (todoText.isNotEmpty()) {
                    updateTodo(todo, todoText)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addTodo(title: String) {
        val todo = Todo(id = ++currentId, title = title)
        todos.add(todo)
        adapter.notifyItemInserted(todos.size - 1)
    }

    private fun updateTodo(todo: Todo, newTitle: String) {
        val position = todos.indexOfFirst { it.id == todo.id }
        if (position != -1) {
            todos[position].title = newTitle
            adapter.notifyItemChanged(position)
        }
    }

    private fun deleteTodo(todo: Todo) {
        val position = todos.indexOfFirst { it.id == todo.id }
        if (position != -1) {
            todos.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }

    private fun updateTodoStatus(todo: Todo, isCompleted: Boolean) {
        val position = todos.indexOfFirst { it.id == todo.id }
        if (position != -1) {
            todos[position].isCompleted = isCompleted
        }
    }
} 