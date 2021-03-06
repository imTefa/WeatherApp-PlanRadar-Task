package com.planradar.task.utils.uiutils

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


@BindingAdapter("app:isVisible")
fun setVisibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}


@BindingAdapter("app:nullableText")
fun setText(textView: TextView, text: String?) {
    text?.let {
        textView.text = it
    }
}

@BindingAdapter("app:txtWatcher")
fun setTextWatcher(editText: EditText, txtWatcher: TextWatcher) {
    editText.addTextChangedListener(txtWatcher)
}