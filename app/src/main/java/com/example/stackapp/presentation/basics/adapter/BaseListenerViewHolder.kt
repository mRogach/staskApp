package com.example.stackapp.presentation.basics.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

abstract class BaseListenerViewHolder<D>(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    abstract fun onBind(item: D, onItemClickListener: BasePagedListAdapter.OnItemClickListener?)

}