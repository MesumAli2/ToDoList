package com.mesum.todolist.ui.addtask


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mesum.todolist.R
import com.mesum.todolist.databinding.FragmentAddUpdateTaskBinding
import com.mesum.todolist.util.onItemSelected
import com.mesum.todolist.util.onRadioButtonSelected
import com.mesum.todolist.util.showDatePickerDialog
import com.mesum.todolist.util.showTimePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private val viewModel: AddTaskViewModel by viewModels()
    private var _binding: FragmentAddUpdateTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModelObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUpdateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupEditTextListeners()
        setupDatePicker()
        setupTimePicker()
        setupCategorySpinner()
        setupPriorityRadioButton()
        setupSaveButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModelObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.viewState.collectLatest { viewState ->
                processViewState(viewState)
                if (viewState.taskAdded)
                    findNavController().navigate(R.id.tasksFragment)

            }
        }
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    private fun setupEditTextListeners() {
        binding.addTaskTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.taskTitleChanged(text.toString().orEmpty())
        }
        binding.addTaskDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.taskDescriptionChanged(text.toString().orEmpty())
        }
    }

    private fun setupDatePicker() {
        binding.datePickerActions.setOnClickListener {
            it.showDatePickerDialog(requireActivity()) { selectedDate ->
                binding.datePickerActions.setText(selectedDate)
                viewModel.taskDueDateChanged(selectedDate)
                binding.timeLl.visibility = View.VISIBLE
            }
        }
    }

    private fun setupTimePicker() {
        binding.timepickerActions.setOnClickListener {
            it.showTimePickerDialog(requireActivity()) { selectedTime ->
                binding.timepickerActions.setText(selectedTime)
                viewModel.takTimeDateChanged(selectedTime)

            }
        }
    }

    private fun setupCategorySpinner() {
        binding.categorySpinner.onItemSelected { selectedCategory ->
            viewModel.taskCategoryChanged(selectedCategory)
        }
    }

    private fun setupPriorityRadioButton() {
        binding.radioGroupPriority.onRadioButtonSelected { selectedRadioButton ->
            val selectedText = selectedRadioButton?.text?.toString() ?: ""
            viewModel.taskPriorityChanged(selectedText)
        }
    }

    private fun setupSaveButton() {
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
}
