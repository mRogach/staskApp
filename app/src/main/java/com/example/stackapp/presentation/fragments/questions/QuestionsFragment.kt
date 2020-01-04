package com.example.stackapp.presentation.fragments.questions

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackapp.R
import com.example.stackapp.databinding.FragmentQuestionsBinding
import com.example.stackapp.presentation.basics.BaseBindModelFragment
import com.example.stackapp.presentation.basics.createViewModel
import com.example.stackapp.presentation.fragments.questions.adapter.QuestionsPagedListAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_questions.*
import kotlinx.android.synthetic.main.fragment_tags.srlListRefresh
import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class QuestionsFragment : BaseBindModelFragment<FragmentQuestionsBinding, QuestionsVM>() {

    @Inject
    lateinit var questionsPagedListAdapter: QuestionsPagedListAdapter

    private val selectedTagArg by lazy { arguments?.getString(KEY_SELECTED_TAG) }

    companion object {

        private const val KEY_SELECTED_TAG = "KEY_SELECTED_TAG"

        fun newInstance(selectedTag: String): DaggerFragment {
            val args = Bundle().apply {
                putString(KEY_SELECTED_TAG, selectedTag)
            }
            return QuestionsFragment().apply { arguments = args }
        }
    }

    override fun getLayoutId() = R.layout.fragment_questions

    override fun initViewModel(): QuestionsVM {
        return createViewModel(viewModelFactory) {
            selectedTag = selectedTagArg ?: ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.questions_title)

        viewModel.startConfigureLoad()
        viewModel.listenConnectivity()

        initAdapter()
        observe()
    }

    private fun initAdapter() {
        rvQuestions.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvQuestions.adapter = questionsPagedListAdapter
        srlListRefresh.apply {
            setColorSchemeColors(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
            setOnRefreshListener {
                if (isRefreshing) {
                    isRefreshing = false
                }
                viewModel.retry()
            }
        }
    }

    private fun observe() {
        viewModel.questions?.observe(
            this@QuestionsFragment,
            Observer { questionsPagedListAdapter.submitList(it) })
        viewModel.questionsDataSourceFactory.sourceLiveData.observe(
            this@QuestionsFragment,
            Observer {
                viewModel.initialLoadState()
                    ?.observe(this, Observer { t -> viewModel.observeInitCommentsState(t) })
                viewModel.getInitLoadError()
                    ?.observe(this, Observer { t -> viewModel.observeInitError(t) })
                viewModel.getNextLoadError()
                    ?.observe(this, Observer { t -> viewModel.observeNextError(t) })
            })
    }
}