package com.mesum.todolist.ui.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mesum.todolist.R
import com.mesum.todolist.databinding.FragmentTaskBinding
import com.mesum.todolist.ui.adapter.TaskListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TasksFragment : Fragment() {


    private val viewModel : TasksViewModel by viewModels()
    private  var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private val adapter = TaskListAdapter(
        onClickCmptTask = { task ->
            viewModel.markTaskAsCompleted(task.id)
        },
        onDeleteTask = { task ->
            viewModel.deleteTask(task.id)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Enable options menu for this fragment

    }
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
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true); // Enable options menu for this fragment

        val fab = binding.addTaskBtn
        fab.setOnClickListener { showAddTask() }
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.viewState.collect() {
                Log.d("TasksViewState", it.toString())
                adapter.submitList(it.allTasks.reversed())
            }
        }
        binding.recyclerViewTask.adapter = adapter



    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        var searchItem = menu.findItem(R.id.action_search)

        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                viewModel.searchTasks(s)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                viewModel.searchTasks(s)
                searchView.clearFocus()
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun showAddTask() {
        val action = TasksFragmentDirections.actionTasksFragmentToAddTaskFragment()
        findNavController().navigate(action)
    }




}