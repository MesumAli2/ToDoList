package com.mesum.todolist.ui.addtask

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mesum.todolist.R
import com.mesum.todolist.TaskViewState
import com.mesum.todolist.data.Task
import com.mesum.todolist.data.TaskStateSerializer
import com.mesum.todolist.data.Tasks
import com.mesum.todolist.data.appStartUpParamsDataStore
import com.mesum.todolist.databinding.FragmentAddUpdateTaskBinding
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language

val Context.dataStore by dataStore("tasks.json", TaskStateSerializer)
class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddUpdateTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddTaskViewModel::class.java]


        // Subscribe to the view-model's view state stateFlow when the view is resumed.
        lifecycleScope.launchWhenResumed {
            viewModel.viewState.collectLatest { viewState ->
                processViewState(viewState)
            }


        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddUpdateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Proxy relevant UI events, such as text changes and button clicks, to the view model
     * for handling.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTaskTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.taskTitleChanged(text.toString().orEmpty())
        }
        binding.addTaskDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.taskDescriptionChanged(text.toString().orEmpty())
        }

        lifecycleScope.launch {

            requireActivity().dataStore.data.collectLatest {
                Log.d("TasksList", it.toString())
            }
        }

        lifecycleScope.launch {
          //  updateTaskTitle("now we add iphone 5", "Iphone 14 pro Max")
        }
        binding.btnSave.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                createTask(Task(title = binding.addTaskTitle.text.toString(), description = binding.addTaskDescription.text.toString()))
//                requireActivity().appStartUpParamsDataStore.updateData {
//                    it.toBuilder().setTitle(binding.addTaskTitle.text.toString()).build()
//                }
//                requireActivity().appStartUpParamsDataStore.updateData { preferences ->
//                    preferences.toBuilder()
//                        .setDescription(binding.addTaskDescription.text.toString()).build()
//                }

            }

            viewModel.createTaskButtonClicked()
        }
    }

    private fun processViewState(viewState: AddTaskViewState) {
        binding.progressCircular.visibility = if (viewState.showProgressBar) {
            View.VISIBLE
        } else View.GONE

        binding.addTaskTitle.error = viewState.error
    }

    companion object {
        const val ARGUMENT_EDIT_TASK_ID = "Update_TASK_ID"

        operator fun invoke(): AddTaskFragment = AddTaskFragment()
    }




    private suspend fun updateTaskTitle(oldTitle: String, newTitle: String) {
        requireActivity().dataStore.updateData { currentData ->
            val updatedTasks = currentData.tasks.toMutableList()

            for (index in updatedTasks.indices) {
                if (updatedTasks[index].title == oldTitle) {
                    updatedTasks[index] = updatedTasks[index].copy(title = newTitle)
                    break // Break out of the loop after updating the first matching task
                }
            }

            currentData.copy(tasks = updatedTasks)
        }
    }

    private suspend fun toggleTaskCompletion(title: String) {
        requireActivity().dataStore.updateData { currentData ->
            val updatedTasks = currentData.tasks.toMutableList()

            for (index in updatedTasks.indices) {
                if (updatedTasks[index].title == title) {
                    val updatedTask = updatedTasks[index].copy(isCompleted = !updatedTasks[index].isCompleted)
                    updatedTasks[index] = updatedTask
                    break // Break out of the loop after updating the first matching task
                }
            }

            currentData.copy(tasks = updatedTasks)
        }
    }
    private suspend fun createTask(task: Task) {
        requireActivity().dataStore.updateData { currentData ->
            val updatedTasks = currentData.tasks.toMutableList()
            updatedTasks.add(task)
            currentData.copy(tasks = updatedTasks)
        }
    }




}