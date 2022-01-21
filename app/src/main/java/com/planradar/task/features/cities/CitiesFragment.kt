package com.planradar.task.features.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.planradar.task.databinding.FragmentCitiesBinding
import com.planradar.task.features.BaseFragment
import com.planradar.task.features.addcity.AddCityFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesFragment : BaseFragment() {

    private lateinit var binding: FragmentCitiesBinding
    private val viewModel by viewModels<CitiesViewModel>()
    private val adapter = CitiesAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCities()

        binding.citiesList.adapter = adapter

        observe()

        binding.btnAddCity.setOnClickListener { openAddCityDialog() }
    }

    private fun observe() {
        observeState()
        observeErrorEvents()
        observeNavigationEvents()
    }

    private fun observeErrorEvents() {
        viewModel.errorState().observe(viewLifecycleOwner) {
            it.consume()?.let { message ->
                showErrorMessage(binding.root, message)
            }
        }
    }

    private fun observeNavigationEvents() {
        viewModel.navAction().observe(viewLifecycleOwner) {
            it.consume()?.let { action ->
                when (action) {
                    is CitiesNavAction.GoToWeatherHistoryScreen -> {
                        findNavController().navigate(
                            CitiesFragmentDirections.actionCitiesFragmentToHistoryFragment(action.city)
                        )
                    }
                    is CitiesNavAction.GoToWeatherScreen -> {
                        findNavController().navigate(
                            CitiesFragmentDirections.actionCitiesFragmentToWeatherFragment(
                                city = action.city,
                                fromHome = true
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeState() {
        viewModel.getUiState().observe(viewLifecycleOwner) {
            binding.mainContainer.isVisible = !it.isEmpty()
            binding.emptyView.isVisible = it.isEmpty()

            adapter.updateList(it.cities)
        }
    }

    private fun openAddCityDialog() {
        val addCityFragment = AddCityFragment.getInstance()
        addCityFragment.show(parentFragmentManager, AddCityFragment.TAG)
    }

}