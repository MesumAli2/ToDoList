package com.mesum.todolist.ui.addtask

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.datastore.dataStore
import androidx.fragment.app.viewModels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mesum.todolist.R
import com.mesum.todolist.data.Task
import com.mesum.todolist.data.local.TaskStateSerializer
import com.mesum.todolist.databinding.FragmentAddUpdateTaskBinding
import com.mesum.todolist.ui.tasks.TasksActivity
import com.mesum.todolist.util.onItemSelected
import com.mesum.todolist.util.onRadioButtonSelected
import com.mesum.todolist.util.showDatePickerDialog
import com.mesum.todolist.util.showTimePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//val Context.dataStore by dataStore("tasks.json", TaskStateSerializer)
@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private val viewModel: AddTaskViewModel by viewModels()

    private var _binding: FragmentAddUpdateTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this)[AddTaskViewModel::class.java]


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
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.addTaskTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.taskTitleChanged(text.toString().orEmpty())
        }
        binding.addTaskDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.taskDescriptionChanged(text.toString().orEmpty())
        }
        binding.datePickerActions.setOnClickListener {
            it.showDatePickerDialog(requireActivity()) { selectedDate ->
                binding.datePickerActions.setText(selectedDate)
                viewModel.taskDueDateChanged(selectedDate)
            }
            binding.timeLl.visibility = View.VISIBLE
        }

        binding.timepickerActions.setOnClickListener {
            it.showTimePickerDialog(requireActivity()){ selectedTime ->
                binding.timepickerActions.setText(selectedTime)
            }
        }
        binding.categorySpinner.onItemSelected { selectedCategory ->
           viewModel.taskCategoryChanged(selectedCategory)
        }

        binding.radioGroupPriority.onRadioButtonSelected { selectedRadioButton ->
            val selectedText = selectedRadioButton?.text?.toString() ?: ""
            // Handle selectedText, even if no RadioButton is selected
            viewModel.taskPriorityChanged(selectedText)
        }

        lifecycleScope.launch {
            viewModel.viewState.collectLatest {
                if (it.taskAdded){
                    findNavController().navigate(R.id.tasksFragment)
                }
            }
          //  updateTaskTitle("now we add iphone 5", "Iphone 14 pro Max")
        }
        binding.btnSave.setOnClickListener {
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




//    private suspend fun updateTaskTitle(oldTitle: String, newTitle: String) {
//        requireActivity().dataStore.updateData { currentData ->
//            val updatedTasks = currentData.tasks.toMutableList()
//
//            for (index in updatedTasks.indices) {
//                if (updatedTasks[index].title == oldTitle) {
//                    updatedTasks[index] = updatedTasks[index].copy(title = newTitle)
//                    break // Break out of the loop after updating the first matching task
//                }
//            }
//
//            currentData.copy(tasks = updatedTasks)
//        }
//    }

//    private suspend fun toggleTaskCompletion(title: String) {
//        requireActivity().dataStore.updateData { currentData ->
//            val updatedTasks = currentData.tasks.toMutableList()
//
//            for (index in updatedTasks.indices) {
//                if (updatedTasks[index].title == title) {
//                    val updatedTask = updatedTasks[index].copy(isCompleted = !updatedTasks[index].isCompleted)
//                    updatedTasks[index] = updatedTask
//                    break // Break out of the loop after updating the first matching task
//                }
//            }
//
//            currentData.copy(tasks = updatedTasks)
//        }
//    }
//    private suspend fun createTask(task: Task) {
//        requireActivity().dataStore.updateData { currentData ->
//            val updatedTasks = currentData.tasks.toMutableList()
//            updatedTasks.add(task)
//            currentData.copy(tasks = updatedTasks)
//        }
//    }




}