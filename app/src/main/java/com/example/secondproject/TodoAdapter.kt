package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.R
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter (
    private val tasks : MutableList<String>,
    private val onDelete: (Int)-> Unit,
    private val onItenClicked:(Int) -> Unit
): RecyclerView.Adapter<TodoAdapter.TaskViewHolder>(){
    inner class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewTask)

        init {
            view.setOnClickListener {
                onItenClicked(adapterPosition)
                true
            }
            view.setOnLongClickListener {
                onItenClicked(adapterPosition)
                true
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int):TaskViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.textView.text = tasks[position]
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
    fun updateTasks(newTasks:List <String>){
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

}
