package com.planradar.task.features

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {


    fun showErrorMessage(view: View, errorMessage: String) {
        Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show()
    }


    fun onBackActionClicked() {
        requireActivity().onBackPressed()
    }

}