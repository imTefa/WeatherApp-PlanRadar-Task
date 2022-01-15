package com.planradar.task.utils.uiutils

class Consumable<T>(private val value: T) {

    private var consumed = false

    fun consume(): T? {
        return if (!consumed) {
            consumed = true
            value
        } else {
            null
        }
    }

    fun orElse(other: T): T {
        return if (!consumed) {
            consumed = true
            value
        } else {
            other
        }
    }
}