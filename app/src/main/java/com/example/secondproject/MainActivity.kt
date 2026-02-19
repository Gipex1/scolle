package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTask: EditText
    private lateinit var buttonAdd: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoAdapter
    private val tasks = mutableListOf<String>()
    private val PREFS_NAME = "todo_prefs"
    private val TASK_KEY = "tasks|"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editTextTask = findViewById(R.id.editTextText)
        buttonAdd=findViewById(R.id.button)
        recyclerView = findViewById(R.id.recyclerView)


        recyclerView.layoutManager= LinearLayoutManager(this)
        adapter = TodoAdapter(tasks,
            onDelete = { position ->
                tasks.removeAt(position)
                adapter.notifyItemRemoved(position)
                saveTasks() // новый список задач
                Toast.makeText(this, "Task deleted", Toast.LENGTH_LONG).show()
            },
            onItemClicked = {position ->
                AlertDialog.Builder(this)
                    .setTitle("Task")
                    .setMessage(tasks[position])
                    .setPositiveButton("ok",null)
                    .show()
            })

        recyclerView.adapter=adapter

        loadTasks() // load all tasks

        buttonAdd.setOnClickListener{
            val task  = editTextTask.text.toString().trim()
            if(task.isEmpty()){
                tasks.add(task)
                adapter.notifyItemInserted(tasks.size-1)
                editTextTask.setText("")
                saveTasks()
                Toast.makeText(this, "Task added", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Task is null", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun saveTasks(){
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        prefs.edit().putStringSet(TASK_KEY, tasks.toSet()).apply()
    }

    private fun loadTasks(){
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savetask = prefs.getStringSet(TASK_KEY, emptySet()) ?: emptyList()
        tasks.clear()
        tasks.addAll(savedTasks)
        adapter.updateTasks(tasks)
    }

}