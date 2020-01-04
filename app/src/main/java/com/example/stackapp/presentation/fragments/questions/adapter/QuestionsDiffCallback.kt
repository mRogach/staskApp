package com.example.stackapp.presentation.fragments.questions.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.stackapp.data.models.question.Question
import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class QuestionsDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Question>() {

    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.creationDate == newItem.creationDate
    }
}