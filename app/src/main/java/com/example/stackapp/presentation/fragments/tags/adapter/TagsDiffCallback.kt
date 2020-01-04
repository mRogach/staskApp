package com.example.stackapp.presentation.fragments.tags.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.stackapp.data.models.tag.Tag
import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class TagsDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Tag>() {

    override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.count == newItem.count
    }
}