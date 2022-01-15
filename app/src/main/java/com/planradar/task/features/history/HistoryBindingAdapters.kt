package com.planradar.task.features.history

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.planradar.task.features.weather.formatFetchDate


@BindingAdapter("app:recordDate")
fun setRecordDate(textView: TextView, date: Long) {
    textView.text = formatFetchDate(date)
}