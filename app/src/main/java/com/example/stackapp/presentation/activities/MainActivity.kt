package com.example.stackapp.presentation.activities

import android.os.Bundle
import com.example.stackapp.R
import com.example.stackapp.presentation.basics.BaseActivity
import com.example.stackapp.presentation.fragments.tags.TagsFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class MainActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            replaceFragment(R.id.fragmentContainer, TagsFragment(), false)
        }
    }
}
