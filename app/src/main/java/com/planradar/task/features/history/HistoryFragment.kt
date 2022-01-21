package com.planradar.task.features.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.planradar.task.R
import com.planradar.task.databinding.FragmentHistoryBinding
import com.planradar.task.features.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment() {


    private lateinit var binding: FragmentHistoryBinding
    private val args by navArgs<HistoryFragmentArgs>()
    private val viewModel by viewModels<HistoryViewModel>()
    private val adapter = HistoryAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupSupportActionBar(title = "${args.city.name} historical", displayHomeAsUpEnabled = true)

        binding.listHistory.adapter = adapter

        viewModel.getHistoryRecord(args.city.id!!)

        observe()
    }

    private fun observe() {
        observeState()
        observeNavAction()
    }

    private fun observeNavAction() {
        viewModel.navAction().observe(this) {
            it.consume()?.let { action ->
                when (action) {
                    is HistoryNavAction.GoToHistoryDetails -> {
                        findNavController().navigate(
                            HistoryFragmentDirections.actionHistoryFragmentToWeatherFragment(
                                weather = action.weather
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeState() {

        viewModel.state().observe(this) { state ->
            binding.state = state
            if (state.isError)
                showErrorMessage(
                    binding.root,
                    state.error ?: getString(R.string.error_common)
                )

            adapter.updateList(state.history)

        }
    }

}