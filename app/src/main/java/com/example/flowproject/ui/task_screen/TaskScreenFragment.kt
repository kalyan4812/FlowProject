package com.example.flowproject.ui.task_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flowproject.R
import com.example.flowproject.adapters.TaskScreenAdapter
import com.example.flowproject.adapters.TaskScreenAdapter.EVENTS.*
import com.example.flowproject.models.Task
import com.example.flowproject.utils.UIEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_task_screen.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TaskScreenFragment : Fragment() {

    val viewModel: TaskViewModel by viewModels()
    private lateinit var taskScreenAdapter: TaskScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        loadData()
        subscribeToEvents()
        setUpListeners()
    }

    private fun loadData() {
        collectLifecycleAwareFlow(viewModel.getFlowTasks()) {
            taskScreenAdapter.setList(it)
        }
    }

    private fun initRecycler() {
        taskScreenAdapter = TaskScreenAdapter()
        rvTasks.layoutManager = LinearLayoutManager(activity)
        rvTasks.hasFixedSize()
        rvTasks.adapter = taskScreenAdapter
    }

    private fun setUpListeners() {
        floating_add_task.setOnClickListener {
            viewModel.OnEventRecieved(TaskScreenEvent.addTaskClick)
        }

        taskScreenAdapter.setOnClickListener(object : TaskScreenAdapter.ClickListener {
            override fun onClick(task: Task, event: TaskScreenAdapter.EVENTS) {
                when (event) {
                    SELECTED -> {
                        viewModel.OnEventRecieved(TaskScreenEvent.onTaskClick(task))
                    }
                    DELETED -> {
                        viewModel.OnEventRecieved(TaskScreenEvent.deleteTask(task))
                    }
                    CHECKED -> {
                        viewModel.OnEventRecieved(TaskScreenEvent.taskState(task, true))
                    }
                    UNCHECKED -> {
                        viewModel.OnEventRecieved(TaskScreenEvent.taskState(task, false))
                    }

                }
            }

        })
    }

    private fun subscribeToEvents() {
        collectLifecycleAwareChannelFlow(viewModel.ui_events) { event ->
            when (event) {
                is UIEvent.showSnackBar -> {
                    Snackbar.make(requireView(), event.messsage, Snackbar.LENGTH_SHORT)
                        .setAction(event.action) {
                            viewModel.OnEventRecieved(TaskScreenEvent.onUndoClick)
                        }.show()
                }
                is UIEvent.Navigate -> {
                    manageNavigation(event.id!!)

                }
                else -> Unit
            }
        }

    }

    private fun manageNavigation(id: Int) {
        val bundle = Bundle()
        bundle.apply {
            putInt("taskId", if (id == -1) -1 else id)
        }
        findNavController().navigate(
            R.id.action_taskScreenFragment_to_addTaskFragment, bundle
        )
    }

    fun <T> collectLifecycleAwareFlow(
        flow: Flow<T>,
        collect: suspend (T) -> Unit
    ) {
        lifecycleScope.launch {

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collect)
            }


        }
    }

    fun <T> collectLifecycleAwareChannelFlow(
        flow: Flow<T>,
        collect: FlowCollector<T>
    ) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect(collect)
            }
        }
    }
}