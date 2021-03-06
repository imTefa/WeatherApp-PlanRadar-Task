package com.planradar.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Long? = null,
    val name: String,
    val country: String
) : Parcelable {

    fun provideFullName() = "$name, $country"

}
