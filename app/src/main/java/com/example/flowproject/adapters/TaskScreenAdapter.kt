package com.example.flowproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.flowproject.R
import com.example.flowproject.models.Task
import kotlinx.android.synthetic.main.task_item.view.*

class TaskScreenAdapter : RecyclerView.Adapter<TaskScreenAdapter.TaskViewHolder>(),
    LifecycleObserver {


    private val callback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title;
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

    private val differ =
        AsyncListDiffer(this, callback) // runs asynchronously,calculates diff b/w old and new list.


    fun setList(tasks: List<Task>) {
        differ.submitList(tasks)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = differ.currentList[position]

        holder.itemView.apply {
            task_title.text = task.title
            tasK_description.text = task.description
            setOnClickListener {
                val pos = holder.adapterPosition
                if (pos != -1) {
                    onClickListener.onClick(differ.getCurrentList().get(pos), EVENTS.SELECTED)
                }
            }
            delete_task.setOnClickListener {
                val pos = holder.adapterPosition
                if (pos != -1) {
                    onClickListener.onClick(differ.getCurrentList().get(pos), EVENTS.DELETED)
                }
            }

            checkbox.isChecked = task.isDone

            checkbox.setOnCheckedChangeListener { p0, checked ->
                val pos = holder.adapterPosition
                if (pos != -1) {
                    onClickListener.onClick(
                        differ.getCurrentList().get(pos),
                        if (checked) EVENTS.CHECKED else EVENTS.UNCHECKED
                    )
                }
            }
        }
    }

    override fun getItemCount() =
        differ.currentList.size


    private lateinit var onClickListener: ClickListener

    fun interface ClickListener { // fun interface will have exactly one method.
        fun onClick(task: Task, events: EVENTS)
    }


    enum class EVENTS {
        SELECTED, DELETED, CHECKED, UNCHECKED
    }

    fun setOnClickListener(clickListener: ClickListener) {
        this.onClickListener = clickListener
    }
}