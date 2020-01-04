package com.example.stackapp.presentation.fragments.questions.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.stackapp.R
import com.example.stackapp.data.models.question.Question
import com.example.stackapp.presentation.basics.adapter.BaseListenerViewHolder
import com.example.stackapp.presentation.basics.adapter.BasePagedListAdapter
import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class QuestionsPagedListAdapter @Inject constructor(diffUtilCallback: DiffUtil.ItemCallback<Question>, val context: Context) :
    BasePagedListAdapter<Question, QuestionsPagedListAdapter.QuestionViewHolder>(diffUtilCallback) {

    override fun getItemViewId() = R.layout.item_question

    override fun instantiateViewHolder(view: View?) = QuestionViewHolder(view)

    inner class QuestionViewHolder(itemView: View?) : BaseListenerViewHolder<Question>(itemView) {

        private val tvTitle by lazy { itemView?.findViewById(R.id.tvTitle) as TextView? }
        private val tvOwnerName by lazy { itemView?.findViewById(R.id.tvOwnerName) as TextView? }
        private val tvDate by lazy { itemView?.findViewById(R.id.tvDate) as TextView? }
        private val ivOwnerPhoto by lazy { itemView?.findViewById(R.id.ivOwnerPhoto) as AppCompatImageView? }

        override fun onBind(item: Question, onItemClickListener: OnItemClickListener?) {
            tvOwnerName?.text = item.owner.displayName
            tvTitle?.text = item.title
            tvDate?.text = item.dateForUI

            ivOwnerPhoto?.let {
                Glide.with(context)
                    .load(item.owner.profileImage)
                    .apply(
                        RequestOptions.circleCropTransform()
                            .error(R.drawable.ic_account_circle_blue)
                            .placeholder(R.drawable.ic_account_circle_blue))
                    .into(it)
            }
        }

    }
}