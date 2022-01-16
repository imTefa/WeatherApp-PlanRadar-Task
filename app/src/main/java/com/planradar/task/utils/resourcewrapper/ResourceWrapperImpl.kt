package com.planradar.task.utils.resourcewrapper

import android.content.Context

internal class ResourceWrapperImpl(
    private val context: Context
) : ResourceWrapper {

    override fun getString(id: Int): String {
        return context.getString(id)
    }


}