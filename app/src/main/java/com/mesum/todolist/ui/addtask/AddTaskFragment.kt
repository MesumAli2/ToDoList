package com.mesum.todolist.ui.addtask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mesum.todolist.R
import com.mesum.todolist.databinding.FragmentAddUpdateTaskBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest


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
        binding.btnSave.setOnClickListener {
            viewModel.createTaskButtonClicked()
        }
    }

    private fun processViewState(viewState: AddTaskViewState){
        binding.progressCircular.visibility = if (viewState.showProgressBar){
            View.VISIBLE
        }else View.GONE

        binding.addTaskTitle.error = viewState.error
    }

    companion object {
        const val ARGUMENT_EDIT_TASK_ID = "Update_TASK_ID"

        operator fun invoke(): AddTaskFragment = AddTaskFragment()
    }
}