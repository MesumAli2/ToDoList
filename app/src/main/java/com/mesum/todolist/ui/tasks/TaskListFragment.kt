package com.mesum.todolist.ui.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mesum.todolist.R
import com.mesum.todolist.databinding.FragmentTaskBinding
import com.mesum.todolist.ui.adapter.TaskSectionAdapter
import com.mesum.todolist.util.onItemSelected
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private val viewModel: TasksListViewModel by viewModels()
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
        setUpRecyclerView()
        setupCategorySpinner()
        setUpSortSpinner()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

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
        observeTasks(sectionAdapter)
    }

    private fun createSectionAdapter(): TaskSectionAdapter {
        return TaskSectionAdapter(
            onClickCmptTask = { task ->
                viewModel.markTaskAsCompleted(task.id)
            },
            onDeleteTask = { task ->
                viewModel.deleteTask(task.id)
            }
        )
    }

    private fun observeTasks(sectionAdapter: TaskSectionAdapter) {
        lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                Log.d("TasksViewState", state.toString())

                withContext(Dispatchers.Main) {
                    binding.recyclerViewTask.adapter = sectionAdapter
                    sectionAdapter.setTasks(state.allTasks.reversed())
                }

                updateEmptyRecyclerViewMessageVisibility(state.allTasks.isEmpty())
            }
        }
    }

    private fun updateEmptyRecyclerViewMessageVisibility(isEmpty: Boolean) {
        binding.emptyRecyclerViewMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.arrowImageView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun setupCategorySpinner() {
        binding.categorySpinner.onItemSelected { selectedCategory ->
            viewModel.categorySelected(selectedCategory)
        }
    }

    private fun setUpSortSpinner() {
        binding.sortingSpinner.onItemSelected { sortBy ->
            viewModel.sortTasks(sortBy)
        }
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
        val action = TaskListFragmentDirections.actionTasksFragmentToAddTaskFragment()
        findNavController().navigate(action)
    }
}
