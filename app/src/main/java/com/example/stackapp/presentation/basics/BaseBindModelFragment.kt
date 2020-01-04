package com.example.stackapp.presentation.basics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.stackapp.BR
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

abstract class BaseBindModelFragment<T : ViewDataBinding, M : BaseViewModel> : DaggerFragment() {

    protected lateinit var binding: T
    protected lateinit var viewModel: M

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initViewModel(): M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        performDataBinding()
        return binding.root
    }

    private fun performDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    protected fun replaceFragment(
        @IdRes containerId: Int, fragmentToShow: Fragment?,
        backSack: Boolean
    ) {
        if (fragmentToShow != null) {
            activity?.supportFragmentManager?.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            activity?.supportFragmentManager?.let {
                val ft = it.beginTransaction()
                    .replace(containerId, fragmentToShow, fragmentToShow::class.java.simpleName)
                if (backSack)
                    ft.addToBackStack(fragmentToShow::class.java.simpleName)
                ft.commitAllowingStateLoss()
            }

        }
    }

}