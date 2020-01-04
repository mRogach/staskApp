package com.example.stackapp.presentation.basics.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

abstract class BasePagedListAdapter <D, VH : BaseListenerViewHolder<D>>(diffUtilCallback : DiffUtil.ItemCallback<D>): PagedListAdapter<D, VH>(diffUtilCallback) {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(getItemViewId(), parent, false)
        return instantiateViewHolder(view)
    }

    abstract fun getItemViewId() : Int

    abstract fun instantiateViewHolder(view: View?): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let { holder.onBind(it, onItemClickListener) }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}