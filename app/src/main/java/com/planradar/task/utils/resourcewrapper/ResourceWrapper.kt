package com.planradar.task.utils.resourcewrapper

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourceWrapper {

    fun  getString(@StringRes id: Int): String

    //TODO implement other functions in the future
}