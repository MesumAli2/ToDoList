package com.mesum.todolist.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mesum.todolist.R
import com.mesum.todolist.databinding.FragmentTaskBinding
import com.mesum.todolist.ui.adapter.TaskListAdapter
import com.mesum.todolist.ui.addtask.AddTaskActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TasksFragment : Fragment() {


    private val viewModel : TasksViewModel by viewModels()
    private  var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_task)

        fab.setOnClickListener { showAddTask() }
        val adapter = TaskListAdapter{ clickedTask ->
            viewModel.markTaskAsCompleted(clickedTask.id)
        }
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.viewState.collect {
                Log.d("TasksViewState", it.toString())
                adapter.submitList(it.allTasks.reversed())

            }
        }
        binding.recyclerViewTask.adapter = adapter
    }

    private fun showAddTask() {
        val intent = Intent(context, AddTaskActivity::class.java)
        startActivityForResult(intent, AddTaskActivity.REQUEST_ADD_TASK)
    }

}