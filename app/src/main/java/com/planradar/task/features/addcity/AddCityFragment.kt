package com.planradar.task.features.addcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.planradar.task.databinding.FragmentAddCityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCityFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddCityBinding
    private val viewModel by viewModels<AddCityViewModel>()
    private val adapter = AddCityAdapter(emptyList())

    companion object {
        const val TAG = "AddCityFragment"
        fun getInstance() = AddCityFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.citiesList.adapter = adapter

        observe()
    }

    private fun observe() {
        observeStates()
        observeNavigationEvents()
    }

    private fun observeNavigationEvents() {
        viewModel.navAction().observe(viewLifecycleOwner) {
            it.consume()?.let { navAction ->
                when (navAction) {
                    AddCityNavAction.GoBack -> dismiss()
                }
            }
        }
    }

    private fun observeStates() {
        viewModel.state().observe(viewLifecycleOwner) {
            adapter.updateList(it.cities)
        }

        viewModel.errorState().observe(viewLifecycleOwner) {
            it.consume()?.let { errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

}