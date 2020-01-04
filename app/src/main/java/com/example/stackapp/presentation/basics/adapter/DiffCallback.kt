package com.example.stackapp.presentation.basics.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

abstract class DiffCallback<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {

    fun getNewList(): List<T> {
        return newList
    }

    private fun getItem(list: List<T>, position: Int): T? {
        return if (position < list.size)
            list[position]
        else
            null
    }

    protected fun getOldItem(position: Int): T? {
        return getItem(oldList, position)
    }

    protected fun getNewItem(position: Int): T? {
        return getItem(newList, position)
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    protected fun equalsObjects(a: Any?, b: Any?): Boolean {
        return if (a == null && b == null)
            true
        else if (a == null || b == null)
            false
        else
            a == b
    }
}
