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
import com.mesum.todolist.data.Task
import com.mesum.todolist.databinding.FragmentTaskBinding
import com.mesum.todolist.ui.adapter.TaskSectionAdapter
import com.mesum.todolist.util.onItemSelected
import dagger.hilt.android.AndroidEntryPoint
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class TasksFragment : Fragment() {

    private val viewModel: TasksViewModel by viewModels()
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()

        setUpFab()

        // Set up the RecyclerView and data loading
        setUpRecyclerView()

        setupCategorySpinner()

        setUpSortSpinner()
    }

    private fun setupCategorySpinner() {
        binding.categorySpinner.onItemSelected { selectedCategory ->
            viewModel.categorySelected(selectedCategory)
        }
    }

    private fun setUpSortSpinner(){
        binding.sortingSpinner.onItemSelected { sortBy ->
        viewModel.sortTasks(sortBy)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        // Handle search queries
        setSearchViewListener(searchView)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    private fun setUpFab() {
        binding.addTaskBtn.setOnClickListener { showAddTask() }
    }

    private fun setUpRecyclerView() {
        val sectionAdapter = createSectionAdapter()

        lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                Log.d("TasksViewState", state.toString())

                // Update the RecyclerView's adapter with the sectionAdapter
                withContext(Dispatchers.Main) {
                    binding.recyclerViewTask.adapter = sectionAdapter
                    sectionAdapter.setTasks(state.allTasks.reversed())
                }
               if ( state.allTasks.isEmpty() ){
                   binding.emptyRecyclerViewMessage.visibility = View.VISIBLE
                   binding.arrowImageView.visibility = View.VISIBLE
               }else{
                   binding.emptyRecyclerViewMessage.visibility = View.GONE
                   binding.arrowImageView.visibility = View.GONE

               }
            }
        }
    }

    private fun createSectionAdapter(): TaskSectionAdapter {
        return TaskSectionAdapter(
            onClickCmptTask = { task ->
                // Handles task click here
                viewModel.markTaskAsCompleted(task.id)
            },
            onDeleteTask = { task ->
                // Handles task deletion here
                viewModel.deleteTask(task.id)
            }
        )
    }

    private fun setSearchViewListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                viewModel.searchTasks(s)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                viewModel.searchTasks(s)
                return true
            }
        })
    }

    private fun showAddTask() {
        val action = TasksFragmentDirections.actionTasksFragmentToAddTaskFragment()
        findNavController().navigate(action)
    }
}
