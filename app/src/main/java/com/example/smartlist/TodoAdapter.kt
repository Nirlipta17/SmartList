package com.example.smartlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private var todos: MutableList<Todo>,
    private val onEditClick: (Todo) -> Unit,
    private val onDeleteClick: (Todo) -> Unit,
    private val onTodoChecked: (Todo, Boolean) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.todoCheckBox)
        val todoText: TextView = view.findViewById(R.id.todoText)
        val editButton: ImageButton = view.findViewById(R.id.editButton)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        
        holder.todoText.text = todo.title
        holder.checkBox.isChecked = todo.isCompleted
        
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onTodoChecked(todo, isChecked)
        }
        
        holder.editButton.setOnClickListener {
            onEditClick(todo)
        }
        
        holder.deleteButton.setOnClickListener {
            onDeleteClick(todo)
        }
    }

    override fun getItemCount() = todos.size

    fun updateTodos(newTodos: List<Todo>) {
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }
} 