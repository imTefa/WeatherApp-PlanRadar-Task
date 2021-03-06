package com.planradar.task.features.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.planradar.data.models.City
import com.planradar.task.databinding.FragmentWeatherBinding
import com.planradar.task.features.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherFragment : BaseFragment() {

    private val args by navArgs<WeatherFragmentArgs>()
    private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cityName = getCityName()
        binding.appbar.backBtn.setOnClickListener { onBackActionClicked() }

        if (args.fromHome) {
            fetchRemoteWeather(args.city!!)
            observeState()
            //binding.btnTryAgain.setOnClickListener { fetchRemoteWeather(args.city!!) }
        } else {
            binding.state = WeatherUiState(weather = args.weather)
        }
    }

    private fun fetchRemoteWeather(city: City) {
        viewModel.getWeather(city)
    }

    private fun getCityName(): String {
        return if (args.fromHome)
            args.city!!.provideFullName()
        else args.weather!!.provideFullName()
    }

    private fun observeState() {
        viewModel.state().observe(viewLifecycleOwner) {
            binding.state = it
            if (it.isError) {
                showErrorMessage(binding.root, it.error!!)
            }
        }
    }


}