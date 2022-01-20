package com.planradar.task.features

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.planradar.task.R

open class BaseFragment : Fragment() {

    open var homeButtonIcon: Int = R.drawable.ic_arrow_back

    fun setupSupportActionBar(
        title: String = "",
        displayHomeAsUpEnabled: Boolean = false
    ) {
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.let { actionBar ->
                actionBar.title = title
                actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
                actionBar.setHomeAsUpIndicator(homeButtonIcon)
            }
        }
    }




    fun showErrorMessage(view: View, errorMessage: String) {
        Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show()
    }

}