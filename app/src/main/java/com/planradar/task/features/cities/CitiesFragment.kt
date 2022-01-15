package com.planradar.task.features.cities

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.planradar.task.R
import com.planradar.task.databinding.FragmentCitiesBinding
import com.planradar.task.features.BaseFragment
import com.planradar.task.utils.uiutils.dpToPx
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

        setupSupportActionBar(title = getString(R.string.title_cities_fragment))
        setHasOptionsMenu(true)

        viewModel.getCities()

        binding.citiesList.adapter = adapter

        observe()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cities_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_city -> {
                openAddCityDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun openAddCityDialog() {
        //TODO it's better to use dialog fragment or, another fragment for adding the city

        val linearLayout = LinearLayout(requireContext())
        val editText = EditText(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(40)
            ).also {
                it.setMargins(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8))
            }
        }
        linearLayout.addView(editText)

        AlertDialog.Builder(requireContext())
            .setTitle("Add city")
            .setView(linearLayout)
            .setCancelable(false)
            .setPositiveButton("Add") { d, _ ->
                //TODO validate city name input
                val cityName = editText.text.toString()
                viewModel.saveNewCity(cityName)
                d.dismiss()
            }.setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }.show()

    }

}