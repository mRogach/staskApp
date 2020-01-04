package com.example.stackapp.presentation.fragments.tags.adapter

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.example.stackapp.R
import com.example.stackapp.data.models.tag.Tag
import com.example.stackapp.presentation.basics.adapter.BaseListenerViewHolder
import com.example.stackapp.presentation.basics.adapter.BasePagedListAdapter
import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class TagsPagedListAdapter @Inject constructor(
    diffUtilCallback: DiffUtil.ItemCallback<Tag>,
    val context: Context
) :
    BasePagedListAdapter<Tag, TagsPagedListAdapter.TagViewHolder>(diffUtilCallback) {

    override fun getItemViewId() = R.layout.item_tag

    override fun instantiateViewHolder(view: View?) = TagViewHolder(view)

    inner class TagViewHolder(itemView: View?) : BaseListenerViewHolder<Tag>(itemView) {

        private val rlRoot by lazy { itemView?.findViewById(R.id.rlRoot) as RelativeLayout? }
        private val tvTitle by lazy { itemView?.findViewById(R.id.tvTitle) as TextView? }
        private val tvPostsCount by lazy { itemView?.findViewById(R.id.tvPostsCount) as TextView? }

        override fun onBind(item: Tag, onItemClickListener: OnItemClickListener?) {
            tvTitle?.text = item.name
            tvPostsCount?.text = "${item.count} posts"
            rlRoot?.setOnClickListener { onItemClickListener?.onItemClick(adapterPosition) }

            rlRoot?.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    if (item.name == "android") android.R.color.holo_green_light else R.color.colorPrimary
                )
            )
        }

    }
}