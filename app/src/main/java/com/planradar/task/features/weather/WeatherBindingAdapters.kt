package com.planradar.task.features.weather

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.planradar.task.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:weatherIconId")
fun setWeatherIcon(imageView: ImageView, iconId: String?) {
    iconId?.let {
        Picasso.get().load(buildWeatherIconUrl(it)).placeholder(R.drawable.ic_cloud).into(imageView)
    }
}

@BindingAdapter("app:cityName", "app:weatherFetchDate")
fun setWeatherFetchDate(textView: TextView, cityName: String?, date: Long?) {
    if (!cityName.isNullOrEmpty() && date != null)
        textView.text =
            textView.context.getString(R.string.txt_weather_date, cityName, formatFetchDate(date))
}


private fun buildWeatherIconUrl(iconId: String): String {
    return "https://openweathermap.org/img/w/$iconId.png"
}

fun formatFetchDate(date: Long): String {
    val format = SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault())
    return format.format(date)
}